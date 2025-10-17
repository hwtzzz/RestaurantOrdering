<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator="/" style="height: 35px;" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="'/adminHome'">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索内容 -->
        <el-input placeholder="菜品名称" prefix-icon="el-icon-search" v-model.trim="dishesName" clearable
            style="width: 200px;"></el-input>
        <el-select v-model="dishesType" placeholder="请选择分类" clearable style="width: 200px;margin-left:10px;">
            <el-option v-for="item in dishesTypeList" :key="item.dishesTypeId" :label="item.dishesTypeName"
                :value="item.dishesTypeId">
            </el-option>
        </el-select>

        <el-button type="primary" icon="el-icon-search" @click="search" style="margin-left: 10px;">搜索</el-button>

        <!-- 添加按钮 -->
        <el-button type="primary" icon="el-icon-plus" @click="addNotice()" style="margin-left: 10px;">添加菜品</el-button>

        <!-- excel导出 -->
        <el-button type="primary" style="margin-left: 20px;" @click="exportExcelSelect"
            icon="el-icon-folder-add">导出Excel文件</el-button>
        <!--excel导出预览弹窗表格-->
        <el-dialog title="表格保存预览" width="70%" :visible.sync="selectWindow">
            <el-table :data="selectData" id="selectTable" height="380px">
                <el-table-column prop="dishesName" label="菜品图片">
                </el-table-column>
                <el-table-column prop="dishesCount" label="菜品库存">
                </el-table-column>
                <el-table-column prop="dishesDesc" label="菜品介绍">
                </el-table-column>
                <el-table-column prop="dishesType" label="菜品分类">
                    <template slot-scope="scope">
                        {{ getName(scope.row.dishesType) }}
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="菜品价格">
                    <template slot-scope="scope">
                        {{ scope.row.price + '￥' }}
                    </template>
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
            <el-table-column prop="dishesImage" label="菜品图片" width="120">
                <template slot-scope="scope">
                    <img :src="scope.row.dishesImage" alt="" srcset="" width="90px" height="90px">
                </template>
            </el-table-column>
            <el-table-column prop="dishesName" label="菜品名称" width="200">
            </el-table-column>
            <el-table-column prop="dishesCount" label="菜品库存" width="100">
            </el-table-column>
            <el-table-column prop="dishesDesc" label="菜品介绍">
            </el-table-column>
            <el-table-column prop="dishesType" label="菜品分类" width="200">
                <template slot-scope="scope">
                    {{ getName(scope.row.dishesType) }}
                </template>
            </el-table-column>
            <el-table-column prop="price" label="菜品价格" width="100">
                <template slot-scope="scope">
                    {{ scope.row.price + '￥' }}
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" width="300">
                <template slot-scope="scope">
                    <!-- 修改信息按钮 -->
                    <el-button type="primary" size="mini" @click="updateOpen(scope.row)"
                        icon="el-icon-edit">修改菜品信息</el-button>

                    <!-- 删除信息按钮 -->
                    <el-popconfirm v-model="visible" title="确定要删除此用户吗？" @confirm='handleConfirm(scope.row)'
                        @cancel='visible = false'>
                        <el-button type="danger" size="mini" slot="reference" style="margin-left: 10px;"
                            icon="el-icon-delete-solid">删除菜品</el-button>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>

        <!-- 添加弹窗 -->
        <el-dialog title="添加菜品" :visible.sync="addFormVisible" :show-close="false">
            <el-form :model="addform">
                <el-form-item label="菜品名称" :label-width="formLabelWidth">
                    <el-input v-model="addform.dishesName" autocomplete="off" required maxlength="20"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品库存" :label-width="formLabelWidth">
                    <el-input v-model="addform.dishesCount" autocomplete="off" type="number" required maxlength="5"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品介绍" :label-width="formLabelWidth">
                    <el-input v-model="addform.dishesDesc" type="textarea" autocomplete="off" required maxlength="200"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品价格" :label-width="formLabelWidth">
                    <el-input v-model="addform.price" autocomplete="off" type="number" required maxlength="5"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品分类" :label-width="formLabelWidth">
                    <el-select v-model="addform.dishesType" placeholder="请选择分类" required>
                        <el-option v-for="item in dishesTypeList" :key="item.dishesTypeId" :label="item.dishesTypeName"
                            :value="item.dishesTypeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="菜品图片" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :on-success="setUrl" :limit="1"
                        ref='upload1'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="addform.dishesImage" alt="">
                    </el-dialog>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">取 消</el-button>
                <el-button type="primary" @click="addSubmit()">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 修改弹窗 -->
        <el-dialog title="修改人员信息" :visible.sync="updateVisible" :show-close="false">
            <el-form :model="form">
                <el-form-item label="菜品名称" :label-width="formLabelWidth">
                    <el-input v-model="form.dishesName" autocomplete="off" required maxlength="20"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品库存" :label-width="formLabelWidth">
                    <el-input v-model="form.dishesCount" autocomplete="off" required maxlength="5"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品介绍" :label-width="formLabelWidth">
                    <el-input v-model="form.dishesDesc" type="textarea" autocomplete="off" required maxlength="200"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品价格" :label-width="formLabelWidth">
                    <el-input v-model="form.price" autocomplete="off" type="number" required maxlength="5"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="菜品分类" :label-width="formLabelWidth">
                    <el-select v-model="form.dishesType" placeholder="请选择分类" required>
                        <el-option v-for="item in dishesTypeList" :key="item.dishesTypeId" :label="item.dishesTypeName"
                            :value="item.dishesTypeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="菜品图片" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview2" :on-remove="handleRemove2" :on-success="setUrl2"
                        :limit="1" ref='upload2'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible2">
                        <img width="100%" :src="form.dishesImage" alt="">
                    </el-dialog>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">取 消</el-button>
                <el-button type="primary" @click="updateSubmit()">确 定</el-button>
            </div>
        </el-dialog>

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
import { addDishes } from '@/api/DishesAPI.js';
import { updateDishes } from '@/api/DishesAPI.js';
import { searchDishes } from '@/api/DishesAPI.js';
import { deleteDishes } from '@/api/DishesAPI.js';
import { getDishesList } from '@/api/DishesAPI.js';
import htmlToExcel from '@/utils/htmlToExcel.js'
import { getDishesTypeList } from '@/api/DishesTypeAPI';
export default {
    name: 'Dishes',

    data() {
        return {
            //表格中选中的数据
            selectData: [],
            selectWindow: false,
            tableData: [],
            dishesType: '',
            dishesName: '',
            total: 0,  //总数据条数
            currentpage: 1,  //当前所在页默认是第一页
            pagesize: 5,  //每页显示多少行数据 默认设置为10
            addFormVisible: false,
            visible: false,
            dialogFormVisible: false,
            formLabelWidth: '120px',
            dialogVisible: false,
            dialogVisible2: false,
            form: {
                dishesId: '',
                dishesName: '',
                dishesImage: '',
                dishesCount: '',
                dishesDesc: '',
                dishesType: '',
                price: '',
                count: ''
            },
            addform: {
                dishesName: '',
                dishesImage: '',
                dishesCount: '',
                dishesDesc: '',
                dishesType: '',
                price: ''
            },
            updateVisible: false,
            dishesTypeList: []
        };
    },

    mounted() {

    },

    methods: {
        getName(dishesType) {
            return this.dishesTypeList.find(item => item.dishesTypeId == dishesType).dishesTypeName
        },
        quxiao() {
            this.addFormVisible = false
            this.updateVisible = false
            this.$refs.upload1.clearFiles();
            this.$refs.upload2.clearFiles();
        },
        //导出
        exportExcel() {
            htmlToExcel.getExcel('#selectTable', '菜品信息')
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
        async handleCurrentChange(currentPage) {
            let { data: res } = await getDishesList(currentPage, 5)
            this.tableData = res.result.records
            this.total = res.result.total
        },
        async updateSubmit() {
            let isEmpty = this.$infoRule(this.form)
            if (isEmpty == true) {
                this.$message.error("信息不完整!")
                return;
            }
            let { data: res } = await updateDishes(this.form)
            if (res.success) {
                this.$refs.upload2.clearFiles();
                this.$message.success("更新成功")
                this.updateVisible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        updateOpen(row) {
            this.form = row
            this.updateVisible = true
        },
        async handleConfirm(row) {
            let { data: res } = await deleteDishes(row.dishesId)
            if (res.success) {
                this.$message.success("删除" + row.dishesName + "成功")
                this.tableData = res.result.records
                this.total = res.result.total
                this.visible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        async addSubmit() {
            let isEmpty = this.$infoRule(this.addform)
            if (isEmpty == true) {
                this.$message.error("信息不完整!")
                return;
            }
            let { data: res } = await addDishes(this.addform)
            if (res.success) {
                this.$message.success("添加成功!")
                this.tableData = res.result.records
                this.total = res.result.total
                this.$refs.upload1.clearFiles();
                this.addFormVisible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        addNotice() {
            this.addform.dishesName = ''
            this.addform.dishesImage = ''
            this.addform.dishesCount = ''
            this.addform.dishesDesc = ''
            this.addform.dishesType = ''
            this.addform.price = ''
            this.addFormVisible = true
        },
        async search() {
            if (this.dishesName == '' && this.dishesType == '') {
                let { data: res } = await getDishesList(1, 5)
                this.tableData = res.result.records
                this.total = res.result.total
            } else {
                let { data: res } = await searchDishes(this.dishesName, this.dishesType)
                if (res.success) {
                    this.tableData = res.result
                    this.total = res.result.length
                    this.$message.success("搜索成功!")
                } else {
                    this.$message.error(res.msg)
                }
            }
        },
        handleRemove(file, fileList) {
            this.addform.dishesImage = ''
        },
        setUrl(response, file, fileList) {
            this.addform.dishesImage = response
        },
        handlePictureCardPreview(file) {
            this.addform.dishesImage = file;
            this.dialogVisible = true;
        },
        handleRemove2(file, fileList) {
            this.form.dishesImage = ''
        },
        setUrl2(response, file, fileList) {
            this.form.dishesImage = response
        },
        handlePictureCardPreview2(file) {
            this.form.dishesImage = file;
            this.dialogVisible2 = true;
        }
    },
    async created() {
        let { data: res } = await getDishesList(1, 5)
        this.tableData = res.result.records
        this.total = res.result.total
        let { data: r } = await getDishesTypeList()
        this.dishesTypeList = r.result
    }
};
</script>

<style lang="less" scoped></style>