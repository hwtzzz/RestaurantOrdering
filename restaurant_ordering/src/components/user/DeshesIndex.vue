<template>
  <div class="store-page">

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="searchKeyword" placeholder="搜索菜品" prefix-icon="el-icon-search" clearable />
      <el-select v-model="filterStatus" placeholder="全部" class="filter-select">
        <el-option label="全部" value="all" />
        <el-option v-for="i in dishesTypeList" :label="i.dishesTypeName" :value="i.dishesTypeId" />
      </el-select>

      <el-badge :value="count" class="item">
        <el-button @click="openCar()" icon="el-icon-shopping-cart-1">购物车</el-button>
      </el-badge>

    </div>
    <!-- 美食列表 -->
    <div class="store-list">
      <el-row :gutter="20">
        <el-col v-for="dishes in filteredStores" :key="dishes.dishesId" :xs="24" :sm="12" :md="8" :lg="6">
          <div class="store-card">
            <div class="store-image">
              <img :src="dishes.dishesImage" />
              <div class="status-tag available">
                {{ getName(dishes.dishesType) }}
              </div>
            </div>
            <div class="store-info">
              <h3 class="store-name">{{ dishes.dishesName }}</h3>
              <div class="desc">
                {{ dishes.dishesDesc }}
              </div>
              <div style="display: flex;justify-content: space-between;align-items: center">
                <div class="store-price">价格：{{ dishes.price + '￥' }}</div>
                <div class="store-price">销量：{{ dishes.count }}</div>
                <div class="adoptBtn" @click="addCar(dishes.dishesName, dishes.dishesId)">+</div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-dialog title="购物车" :visible.sync="dialogTableVisible">
      <el-table :data="gridData">
        <el-table-column property="dishesId" label="美食名称">
          <template slot-scope="scope">
            {{ getDishesName(scope.row.dishesId) }}
          </template>
        </el-table-column>
        <el-table-column property="count" label="数量"></el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="deleteCar(scope.row)"
              icon="el-icon-delete-solid">删除菜品</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">我再想想</el-button>
        <el-button type="primary" @click="addOrder()" :loading="submitting">下 单</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getAllDishes } from '@/api/DishesAPI';
import { getDishesTypeList } from '@/api/DishesTypeAPI';
import { addCar, getMyCarCount, deleteCar } from '@/api/CarAPI';
import { getMyCarList } from '@/api/CarAPI';
import { addOrder } from '@/api/OrderAPI';
export default {
  name: 'RestaurantOrderingDeshesIndex',

  data() {
    return {
      submitting: false,
      searchKeyword: '',
      filterStatus: 'all',
      dishesList: [], // 从后端获取的数据
      dishesTypeList: [],
      count: '',
      dialogTableVisible: false,
      gridData: [],
      user: '',
      dishesTypeMap: new Map()
    };
  },
  computed: {
    filteredStores() {
      return this.dishesList.filter(dishes => {
        const keywordMatch =
          dishes.dishesName?.includes(this.searchKeyword)
        const statusMatch =
          this.filterStatus === 'all' ||
          dishes.dishesType === this.filterStatus;
        return keywordMatch && statusMatch;
      });
    }
  },
  mounted() {

  },
  methods: {
    async deleteCar(row) {
      let { data: res } = await deleteCar(row.carId)
      this.gridData = res.result
      this.$message.success("已删除菜品")
      this.count = this.count - 1
      if (this.count == 0) {
        this.dialogTableVisible = false
      }
    },
    async addOrder() {
      this.submitting = true
      let { data: res } = await addOrder(this.user.userId)
      if (res.success) {
        this.count = 0
        this.$message.success("已下单!")
        this.dialogTableVisible = false
        this.submitting = false
      } else {
        this.$message.error(res.msg)
      }
    },
    getDishesName(dishesId) {
      return this.dishesList.find(item => item.dishesId == dishesId).dishesName
    },
    async openCar() {
      if (this.count > 0) {
        let { data: res } = await getMyCarList(this.user.userId)
        this.gridData = res.result
        this.dialogTableVisible = true
      } else {
        this.$message({
          message: "您的购物车是空的哦~",
          type: 'warning'
        });
      }
    },
    getName(dishesType) {
      return this.dishesTypeMap.get(dishesType)?.dishesTypeName || '未知类型'
    },
    async addCar(dishesName, dishesId) {
      let { data: res } = await addCar(this.user.userId, dishesId)
      if (res.success) {
        this.count = res.result
        this.$notify({
          // title: '成功',
          message: dishesName + "已添加到购物车",
          type: 'success'
        });
      } else {
        this.$message.error(res.msg)
      }
    }
  },
  async created() {
    let { data: res } = await getAllDishes()
    this.dishesList = res.result
    let { data: r } = await getDishesTypeList()
    this.dishesTypeList = r.result
    this.user = JSON.parse(localStorage.getItem("user"))
    let { data: c } = await getMyCarCount(this.user.userId)
    this.count = c.result
    this.dishesTypeMap = new Map(
      this.dishesTypeList.map(item => [item.dishesTypeId, item])
    )
  }
};
</script>
<style lang="less">
.desc {
  height: 100px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  line-clamp: 5;
  -webkit-box-orient: vertical;
  font-size: 13px;
  color: #606266;
}

.adoptBtn {
  height: 30px;
  width: 30px;
  background-color: #00d2fc;
  border-radius: 20px;
  color: white;
  font-size: 14px;
  text-align: center;
  line-height: 30px;
  cursor: pointer;
}

.store-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;

  .search-bar {
    display: flex;
    gap: 15px;
    margin-bottom: 30px;

    .el-input {
      flex: 1;
    }

    .filter-select {
      width: 150px;
    }
  }

  .store-list {
    .store-card {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
      overflow: hidden;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-5px);
      }

      .store-image {
        position: relative;
        height: 200px;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }

        .status-tag {
          position: absolute;
          top: 10px;
          right: 10px;
          padding: 6px 12px;
          border-radius: 20px;
          font-size: 12px;
          color: white;

          &.available {
            background: rgba(19, 206, 102, 0.8);
          }
        }
      }

      .store-info {
        padding: 15px;

        .store-name {
          font-size: 18px;
          margin: 0 0 10px;
          color: #303133;
        }

        .store-price {
          color: #E6A23C;
          font-weight: bold;
          margin: 8px 0;
        }

        .store-address {
          color: #606266;
          font-size: 14px;
        }
      }
    }
  }
}
</style>