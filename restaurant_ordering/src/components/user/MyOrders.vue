<template>
    <div class="order-container">
        <!-- 订单卡片 -->
        <el-card v-for="order in orders" :key="order.ordersId" class="order-card" shadow="hover">
            <!-- 订单头部 -->
            <div class="order-header">
                <div>
                    <span class="order-id">订单号：#{{ order.ordersId }}</span>
                    <el-tag :type="statusConfig[order.flag].color" effect="dark" class="status-tag">
                        {{ statusConfig[order.flag].text }}
                    </el-tag>
                </div>
                <span class="order-time">{{ order.orderTime }}</span>
            </div>

            <!-- 菜品详情 -->
            <el-descriptions title="菜品清单" :column="2" class="dish-list">
                <el-descriptions-item v-for="(dish, index) in parseDishes(order.dishesNames)" :key="index"
                    :label="dish.name">
                    {{ dish.quantity }}份
                    <span class="dish-price">(￥{{ getDishesPrice(dish.name) }}/份)</span>
                </el-descriptions-item>
            </el-descriptions>

            <!-- 订单尾部 -->
            <div class="order-footer">
                <div class="user-info">
                    <el-descriptions :column="2">
                        <el-descriptions-item label="联系人">{{ order.name }}</el-descriptions-item>
                        <el-descriptions-item label="电话">{{ order.phone }}</el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="total-section">
                    总金额：<span class="total-price">￥{{ order.totalPrice }}</span>
                </div>
            </div>

            <!-- 动态操作区 -->
            <div v-if="order.flag === 3" class="action-area">
                <el-button type="primary" icon="el-icon-edit" size="small" @click="showEvaluateDialog(order)">
                    立即评价
                </el-button>
            </div>
        </el-card>


        <el-dialog title="评价本次服务吧" :visible.sync="updateVisible">
            <el-form>
                <el-form-item label="打分" :label-width="formLabelWidth">
                    <el-rate v-model="star" style="margin-top: 10px;" show-text>
                    </el-rate>
                </el-form-item>
                <el-form-item label="评价" :label-width="formLabelWidth">
                    <el-input v-model="appraise" autocomplete="off" type="textarea" :rows="5" maxlength="300"
                        show-word-limit></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">我再想想</el-button>
                <el-button type="primary" @click="updateSubmit()" :loading="submitting">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>
import { getMyOrders, addAppraise } from '@/api/OrderAPI'
import { getAllDishes } from '@/api/DishesAPI'
export default {
    data() {
        return {
            submitting: false,
            updateVisible: false,
            formLabelWidth: '120px',
            orders: [], // 初始假数据示例
            statusConfig: {
                1: { text: '已下单', color: '' },
                2: { text: '制作中', color: 'warning' },
                3: { text: '待评价', color: 'info' },
                4: { text: '已评价', color: 'success' }
            },
            dishesList: [],
            user: '',
            star: '',
            appraise: '',
            ordersId: ''
        }
    },
    created() {
        this.user = JSON.parse(localStorage.getItem("user"))
        this.initializeData()
        this.getAllDishes()
    },
    methods: {
        quxiao() {
            this.updateVisible = false
            this.appraise = ''
            this.star = ''
        },
        // 初始化订单数据
        async initializeData() {
            let { data: res } = await getMyOrders(this.user.userId)
            this.orders = res.result
        },
        // 初始化菜品价格数据
        async getAllDishes() {
            let { data: res } = await getAllDishes()
            this.dishesList = res.result
        },
        getDishesPrice(dishesName) {
            return this.dishesList.find(item => item.dishesName == dishesName).price
        },
        // 解析菜品字符串
        parseDishes(dishesStr) {
            return dishesStr.split('、').map(item => {
                const [name, quantity] = item.split(' * ')
                return {
                    name: name.trim(),
                    quantity: parseInt(quantity)
                }
            })
        },

        // 显示评价弹窗
        showEvaluateDialog(order) {
            this.ordersId = order.ordersId
            this.updateVisible = true
        },

        // 提交评价（模拟接口）
        async updateSubmit() {
            if (this.star == '' || this.appraise == '') {
                this.$message.error("请填写评价或评分信息!")
                return;
            }
            this.submitting = true
            let { data: res } = await addAppraise(this.ordersId, this.star, this.appraise)
            if (res.success) {
                this.orders = res.result
                this.updateVisible = false
                this.$message.success("感谢您的评价!")
            } else {
                this.$message.error(res.msg)
            }
            this.submitting = false
        }
    }
}
</script>
<style lang="less" scoped>
.order-container {
    max-width: 1200px;
    margin: 20px auto;
    padding: 20px;
    background: #f8f9fa;

    .order-card {
        margin-bottom: 20px;
        transition: transform 0.3s, box-shadow 0.3s;

        &:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
    }

    .order-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;

        .order-id {
            font-size: 16px;
            color: #333;
            font-weight: 600;
        }

        .status-tag {
            margin-left: 15px;
            letter-spacing: 1px;
        }

        .order-time {
            color: #909399;
            font-size: 14px;
        }
    }

    .dish-list {
        margin: 20px 0;

        ::v-deep .el-descriptions__title {
            font-size: 15px;
            color: #606266;
        }

        .dish-price {
            color: #f56c6c;
            margin-left: 8px;
        }
    }

    .order-footer {
        margin-top: 15px;
        padding-top: 15px;
        border-top: 1px solid #eee;

        .user-info {
            ::v-deep .el-descriptions__label {
                color: #909399;
                width: 80px;
            }
        }

        .total-price {
            color: #f56c6c;
            font-size: 18px;
            font-weight: bold;
        }
    }

    .action-area {
        margin-top: 15px;
        text-align: right;
    }

    .evaluation-box {
        margin-top: 15px;
        padding: 15px;
        background: #f8f9fa;
        border-radius: 4px;

        .rating-stars {
            margin-bottom: 8px;
        }

        .evaluation-text {
            color: #606266;
            line-height: 1.6;
            font-size: 14px;
        }
    }
}
</style>