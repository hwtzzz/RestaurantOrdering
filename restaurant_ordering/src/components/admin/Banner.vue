<template>
    <div>
        <el-breadcrumb separator="/" style="height: 35px;" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="'/adminHome'">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <el-button type="primary" icon="el-icon-plus" @click="addNotice()" style="margin-left: 10px;">添加轮播</el-button>


        <el-table :data="tableData" style="width: 100%;margin-top: 10px" border :row-class-name="tableRowClassName"
            stripe>
            <el-table-column prop="image" label="轮播图" width="200">
                <template slot-scope="scope">
                    <img :src="scope.row.image" alt="" srcset="" width="90px" height="90px">
                </template>
            </el-table-column>
            <el-table-column prop="title" label="标题">
            </el-table-column>
            <el-table-column prop="description" label="介绍">
            </el-table-column>
            <el-table-column label="操作" width="300">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="updateOpen(scope.row)"
                        icon="el-icon-edit">修改轮播信息</el-button>
                    <el-popconfirm v-model="visible" title="确定要删除此轮播吗？" @confirm='handleConfirm(scope.row)'
                        @cancel='visible = false'>
                        <el-button type="danger" size="mini" slot="reference"
                            style="margin-left: 20px;">删除轮播</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加公告 -->
        <el-dialog title="添加轮播" :visible.sync="addFormVisible" :show-close="false">
            <el-form :model="addform">
                <el-form-item label="标题" :label-width="formLabelWidth">
                    <el-input v-model="addform.title" autocomplete="off" required maxlength="30"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="介绍" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="addform.description" autocomplete="off" required maxlength="100"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="轮播图片" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :on-success="setUrl" :limit="1"
                        ref='upload1'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="addform.image" alt="">
                    </el-dialog>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">取 消</el-button>
                <el-button type="primary" @click="addSubmit()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改轮播信息" :visible.sync="updateVisible" :show-close="false">
            <el-form :model="form">
                <el-form-item label="标题" :label-width="formLabelWidth">
                    <el-input v-model="form.title" autocomplete="off" required maxlength="30"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="介绍" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="form.description" autocomplete="off" required maxlength="100"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="轮播图片" :label-width="formLabelWidth">
                    <el-upload action="http://127.0.0.1:8888/upload" list-type="picture-card"
                        :on-preview="handlePictureCardPreview2" :on-remove="handleRemove2" :on-success="setUrl2"
                        :limit="1" ref='upload2'>
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible2">
                        <img width="100%" :src="form.image" alt="">
                    </el-dialog>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">取 消</el-button>
                <el-button type="primary" @click="updateSubmit()">确 定</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
import { getIndexBanner, addBanner, updateBanner, deleteBanner } from '@/api/BannerAPI';
export default {
    name: 'CslVueUser',

    data() {
        return {
            //表格中选中的数据
            selectData: [],
            selectWindow: false,
            tableData: [],
            addFormVisible: false,
            visible: false,
            dialogFormVisible: false,
            formLabelWidth: '120px',
            dialogVisible: false,
            dialogVisible2: false,
            form: {
                bannerId: '',
                title: '',
                description: '',
                image: ''
            },
            addform: {
                title: '',
                description: '',
                image: ''
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
        tableRowClassName({ row, rowIndex }) {
            if (rowIndex === 1) {
                return 'warning-row';
            } else if (rowIndex === 3) {
                return 'success-row';
            }
            return '';
        },
        async handleCurrentChange(currentPage) {
            let { data: res } = await getAllNotice()
            this.tableData = res.result
        },
        async updateSubmit() {
            let isEmpty = this.$infoRule(this.form)
            if (isEmpty == true) {
                this.$message.error("信息不完整!")
                return;
            }
            let { data: res } = await updateBanner(this.form)
            if (res.success) {
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
            let { data: res } = await deleteBanner(row.bannerId)
            if (res.success) {
                this.$message.success("删除轮播" + row.title + "成功")
                this.tableData = res.result
                this.visible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        async addSubmit() {
            let isEmpty = this.$infoRule(this.addform)
            if (isEmpty == true) {
                this.$message.error("轮播信息不完整!")
                return;
            }
            let { data: res } = await addBanner(this.addform)
            if (res.success) {
                this.$message.success("添加成功!")
                this.tableData = res.result
                this.addFormVisible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        addNotice() {
            this.addform.title = ''
            this.addform.description = ''
            this.addform.image = ''
            this.addFormVisible = true
        },
        handleRemove(file, fileList) {
            this.addform.image = ''
        },
        setUrl(response, file, fileList) {
            this.addform.image = response
        },
        handlePictureCardPreview(file) {
            this.addform.image = file;
            this.dialogVisible = true;
        },
        handleRemove2(file, fileList) {
            this.form.image = ''
        },
        setUrl2(response, file, fileList) {
            this.form.image = response
        },
        handlePictureCardPreview2(file) {
            this.form.image = file;
            this.dialogVisible2 = true;
        }
    },
    async created() {
        let { data: res } = await getIndexBanner()
        this.tableData = res.result
    }
};
</script>