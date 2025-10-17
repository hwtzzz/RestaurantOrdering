<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator="/" style="height: 35px;" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="'/adminHome'">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索内容 -->
        <el-input placeholder="姓名" prefix-icon="el-icon-search" v-model.trim="searchInfo.name" clearable
            style="width: 200px;"></el-input>
        <el-input placeholder="电话" prefix-icon="el-icon-search" v-model.trim="searchInfo.phone" clearable
            style="width: 200px;margin-left: 10px;"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="search" style="margin-left: 10px;">搜索</el-button>

        <!-- 添加按钮 -->
        <el-button type="primary" icon="el-icon-plus" @click="addNotice()" style="margin-left: 10px;">添加员工</el-button>

        <!-- excel导出 -->
        <el-button type="primary" style="margin-left: 20px;" @click="exportExcelSelect"
            icon="el-icon-folder-add">导出Excel文件</el-button>
        <!--excel导出预览弹窗表格-->
        <el-dialog title="表格保存预览" width="70%" :visible.sync="selectWindow">
            <el-table :data="selectData" id="selectTable" height="380px">
                <el-table-column prop="username" label="用户名">
                </el-table-column>
                <el-table-column prop="name" label="姓名">
                </el-table-column>
                <el-table-column prop="phone" label="电话号码">
                </el-table-column>
                <el-table-column prop="sex" label="性别">
                    <template slot-scope="scope">
                        <el-tag type="danger" v-if="scope.row.sex == 2">女</el-tag>
                        <el-tag v-if="scope.row.sex == 1">男</el-tag>
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
            <el-table-column prop="avatar" label="头像" width="120">
                <template slot-scope="scope">
                    <img :src="scope.row.avatar" alt="" srcset="" width="90px" height="90px">
                </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名">
            </el-table-column>
            <el-table-column prop="name" label="姓名">
            </el-table-column>
            <el-table-column prop="phone" label="电话号码">
            </el-table-column>
            <el-table-column prop="sex" label="性别">
                <template slot-scope="scope">
                    <el-tag type="danger" v-show="scope.row.sex == 2">女</el-tag>
                    <el-tag v-if="scope.row.sex == 1">男</el-tag>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" width="360">
                <template slot-scope="scope">
                    <!-- 修改信息按钮 -->
                    <el-button type="primary" size="mini" @click="updateOpen(scope.row)"
                        icon="el-icon-edit">修改用户信息</el-button>

                    <!-- 重置密码 -->
                    <el-button type="primary" size="mini" @click="resetPassword(scope.row)" style="margin-left: 10px;"
                        icon="el-icon-refresh-right">重置密码</el-button>

                    <!-- 删除信息按钮 -->
                    <el-popconfirm v-model="visible" title="确定要删除此用户吗？" @confirm='handleConfirm(scope.row)'
                        @cancel='visible = false'>
                        <el-button type="danger" size="mini" slot="reference" style="margin-left: 10px;"
                            icon="el-icon-delete-solid">删除用户</el-button>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>

        <!-- 添加弹窗 -->
        <el-dialog title="添加用户" :visible.sync="addFormVisible" :show-close="false">
            <el-form :model="addform">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="addform.username" autocomplete="off" required maxlength="15" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="姓名" :label-width="formLabelWidth">
                    <el-input v-model="addform.name" autocomplete="off" required maxlength="10" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="电话" :label-width="formLabelWidth">
                    <el-input v-model="addform.phone" autocomplete="off" type="number" required maxlength="11" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="addform.sex" placeholder="请选择性别" required>
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="头像" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :on-success="setUrl" :limit="1"
                        ref='upload1'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="addform.avatar" alt="">
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
                <el-form-item label="姓名" :label-width="formLabelWidth">
                    <el-input v-model="form.name" autocomplete="off" required maxlength="10" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="电话" :label-width="formLabelWidth">
                    <el-input v-model="form.phone" autocomplete="off" type="text" required maxlength="11" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="form.sex" placeholder="请选择性别" required>
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="修改头像" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview2" :on-remove="handleRemove2" :on-success="setUrl2"
                        :limit="1" ref='upload2'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible2">
                        <img width="100%" :src="form.avatar" alt="">
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
import { addUser } from '@/api/UserAPI.js';
import { updateUser } from '@/api/UserAPI.js';
import { searchUser } from '@/api/UserAPI.js';
import { deleteUser } from '@/api/UserAPI.js';
import { getAllUser, resetPassword } from '@/api/UserAPI.js';
import htmlToExcel from '@/utils/htmlToExcel.js'
export default {
    name: 'User',

    data() {
        return {
            //表格中选中的数据
            selectData: [],
            selectWindow: false,
            options: [
                {
                    value: 1,
                    label: '男'
                },
                {
                    value: 2,
                    label: '女'
                }],
            tableData: [],
            searchInfo: {
                name: '',
                phone: ''
            },
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
                userId: '',
                username: '',
                password: '',
                avatar: '',
                sex: '',
                phone: '',
                name: ''
            },
            addform: {
                username: '',
                avatar: '',
                sex: '',
                phone: '',
                name: ''
            },
            updateVisible: false,
        };
    },

    mounted() {

    },

    methods: {
        quxiao() {
            this.addFormVisible = false
            this.updateVisible = false
            this.$refs.upload1.clearFiles();
            this.$refs.upload2.clearFiles();
        },
        //导出
        exportExcel() {
            htmlToExcel.getExcel('#selectTable', '用户信息')
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
            let { data: res } = await getAllUser(currentPage, 5)
            this.tableData = res.result.records
            this.total = res.result.total
        },
        async resetPassword(row) {
            let { data: res } = await resetPassword(row.userId)
            if (res.success) {
                this.$message.success("密码重置成功")
                this.tableData = res.result.records
                this.total = res.result.total
            } else {
                this.$message.error(res.msg)
            }
        },
        async updateSubmit() {
            let isEmpty = this.$infoRule(this.form)
            if (isEmpty == true) {
                this.$message.error("信息不完整!")
                return;
            }
            let { data: res } = await updateUser(this.form)
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
            let { data: res } = await deleteUser(row.userId)
            if (res.success) {
                this.$message.success("删除" + row.name + "成功")
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
            let { data: res } = await addUser(this.addform)
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
            this.addform.username = ''
            this.addform.avatar = ''
            this.addform.name = ''
            this.addform.phone = ''
            this.addform.sex = ''
            this.addFormVisible = true
        },
        async search() {
            let a = this.searchInfo
            if (a.name == '' && a.phone == '') {
                let { data: res } = await getAllUser(1, 5)
                this.tableData = res.result.records
                this.total = res.result.total
            } else {
                let { data: res } = await searchUser(a.name, a.phone)
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
            this.addform.avatar = ''
        },
        setUrl(response, file, fileList) {
            this.addform.avatar = response
        },
        handlePictureCardPreview(file) {
            this.addform.avatar = file;
            this.dialogVisible = true;
        },
        handleRemove2(file, fileList) {
            this.form.avatar = ''
        },
        setUrl2(response, file, fileList) {
            this.form.avatar = response
        },
        handlePictureCardPreview2(file) {
            this.form.avatar = file;
            this.dialogVisible2 = true;
        }
    },
    async created() {
        let { data: res } = await getAllUser(1, 5)
        this.tableData = res.result.records
        this.total = res.result.total
    }
};
</script>

<style lang="less" scoped></style>