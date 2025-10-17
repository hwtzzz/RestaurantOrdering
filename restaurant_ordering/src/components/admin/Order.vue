<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator="/" style="height: 35px;" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="'/adminHome'">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索内容 -->
        <el-select v-model="flag" placeholder="订单状态" clearable style="width: 200px;">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
        </el-select>
        <!-- 搜索按钮 -->
        <el-button type="primary" icon="el-icon-search" @click="search" style="margin-left: 10px;">搜索</el-button>

        <!-- excel导出 -->
        <el-button type="primary" style="margin-left: 20px;" @click="exportExcelSelect"
            icon="el-icon-folder-add">导出Excel文件</el-button>
        <!--excel导出预览弹窗表格-->
        <el-dialog title="表格保存预览" width="70%" :visible.sync="selectWindow">
            <el-table :data="selectData" id="selectTable" height="380px">
                <el-table-column prop="ordersId" label="订单号">
                </el-table-column>
                <el-table-column prop="orderTime" label="下单时间">
                </el-table-column>
                <el-table-column prop="dishesNames" label="订单信息">
                </el-table-column>
                <el-table-column prop="totalPrice" label="订单金额">
                    <template slot-scope="scope">
                        {{ scope.row.totalPrice + '￥' }}
                    </template>
                </el-table-column>
                <el-table-column prop="flag" label="订单状态">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.flag == 1">已下单</el-tag>
                        <el-tag v-if="scope.row.flag == 2">制作中</el-tag>
                        <el-tag v-if="scope.row.flag == 3">已完成</el-tag>
                        <el-tag v-if="scope.row.flag == 4">已评价</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="下单人">
                </el-table-column>
                <el-table-column prop="phone" label="电话">
                </el-table-column>
                <el-table-column prop="star" label="评分">
                </el-table-column>
                <el-table-column prop="appraise" label="评价内容">
                </el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="exportExcel">确定保存</el-button>
            </div>
        </el-dialog>

        <!-- 数据列表 -->
        <el-table :data="tableData" style="width: 100%;margin-top: 10px" border :row-class-name="tableRowClassName"
            stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="ordersId" label="订单号" width="100">
            </el-table-column>
            <el-table-column prop="orderTime" label="下单时间" width="180">
            </el-table-column>
            <el-table-column prop="dishesNames" label="订单信息">
            </el-table-column>
            <el-table-column prop="totalPrice" label="订单金额" width="150">
                <template slot-scope="scope">
                    {{ scope.row.totalPrice + '￥' }}
                </template>
            </el-table-column>
            <el-table-column prop="name" label="下单人" width="100">
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="150">
            </el-table-column>
            <el-table-column prop="star" label="评分"  width="200">
                <template slot-scope="scope">
                    <span v-if="scope.row.flag != 4">未评分</span>
                    <el-rate v-model="scope.row.star" show-text disabled v-if="scope.row.flag == 4">
                    </el-rate>
                </template>
            </el-table-column>
            <el-table-column prop="appraise" label="订单评价">
                <template slot-scope="scope">
                    <span v-if="scope.row.flag != 4">未评价</span>
                    <span v-if="scope.row.flag == 4">{{ scope.row.appraise }}</span>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <!-- 修改信息按钮 -->
                    <el-button type="primary" plain size="mini" @click="updateOrderFlag(scope.row.ordersId, 2)"
                        icon="el-icon-thumb" v-if="scope.row.flag == 1">点击制作</el-button>

                    <el-button type="warning" plain size="mini" @click="updateOrderFlag(scope.row.ordersId, 3)"
                        icon="el-icon-check" v-if="scope.row.flag == 2">订单完成</el-button>

                    <el-tag type="info" icon="el-icon-check" v-if="scope.row.flag == 3">等待用户评价</el-tag>
                    <el-tag type="success" icon="el-icon-check" v-if="scope.row.flag == 4">用户已评价</el-tag>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-row>
            <el-col style="text-align:left;margin-top: 10px;">
                <el-pagination @current-change="handleCurrentChange" :current-page="currentpage" :page-size="pagesize"
                    layout="total, prev, pager, next" :total="total">
                </el-pagination>
            </el-col>
        </el-row>

    </div>
</template>

<script>
import { getOrdersList, updateOrderFlag, searchOrder } from '@/api/OrderAPI.js'
import htmlToExcel from '@/utils/htmlToExcel.js'
export default {
    name: 'InnovationSystemDishesType',

    data() {
        return {
            options: [
                {
                    value: 1,
                    label: '已下单'
                },
                {
                    value: 2,
                    label: '制作中'
                },
                {
                    value: 3,
                    label: '已完成'
                }],
            //表格中选中的数据
            selectData: [],
            selectWindow: false,
            tableData: [],
            visible: false,
            formLabelWidth: '120px',
            flag: '',
            total: 0,  //总数据条数
            currentpage: 1,  //当前所在页默认是第一页
            pagesize: 8,  //每页显示多少行数据 默认设置为10
        };
    },

    mounted() {

    },

    methods: {
        //导出
        exportExcel() {
            htmlToExcel.getExcel('#selectTable', '订单信息')
        },
        //显示预览弹窗
        exportExcelSelect() {
            if (this.selectData.length < 1) {
                this.$message.error('请选择要导出的内容！');
                return false;
            }
            this.selectWindow = true;
        },
        //选中数据
        handleSelectionChange(val) {
            this.selectData = val;
        },
        tableRowClassName({ row, rowIndex }) {
            if (rowIndex === 1) {
                return 'warning-row';
            } else if (rowIndex === 3) {
                return 'success-row';
            }
            return '';
        },
        async updateOrderFlag(ordersId, flag) {
            let { data: res } = await updateOrderFlag(ordersId, flag)
            if (res.success) {
                this.$message.success("已修改订单状态!")
                this.tableData = res.result.records
                this.total = res.result.total
            } else {
                this.$message.error(res.msg)
            }
        },
        async handleCurrentChange(currentPage) {
            let { data: res } = await getOrdersList(currentPage, 8)
            this.tableData = res.result.records
            this.tableData = res.result.total
        },
        async search() {
            if (this.flag == '') {
                let { data: res } = await getOrdersList(1, 8)
                this.tableData = res.result.records
                this.total = res.result.total
            } else {
                let { data: res } = await searchOrder(this.flag)
                if (res.success) {
                    this.tableData = res.result
                    this.total = res.result.length
                    this.$message.success("搜索成功!")
                } else {
                    this.$message.error(res.msg)
                }
            }
        },
    },
    async created() {
        let { data: res } = await getOrdersList(1, 8)
        this.tableData = res.result.records
        this.total = res.result.total
    }
};
</script>

<style lang="less" scoped></style>