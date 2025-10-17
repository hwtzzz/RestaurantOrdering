<template>
    <div>
        <!-- 面包屑 -->
        <el-breadcrumb separator="/" style="height: 35px;" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="'/adminHome'">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 搜索内容 -->
        <el-input placeholder="分类名称" prefix-icon="el-icon-search" v-model.trim="dishesTypeName" clearable
            style="width: 200px;"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="search" style="margin-left: 10px;">搜索</el-button>

        <!-- 添加按钮 -->
        <el-button type="primary" icon="el-icon-plus" @click="addNotice()" style="margin-left: 10px;">添加分类</el-button>

        <!-- excel导出 -->
        <el-button type="primary" style="margin-left: 20px;" @click="exportExcelSelect"
            icon="el-icon-folder-add">导出Excel文件</el-button>
        <!--excel导出预览弹窗表格-->
        <el-dialog title="表格保存预览" width="70%" :visible.sync="selectWindow">
            <el-table :data="selectData" id="selectTable" height="380px">
                <el-table-column prop="dishesTypeId" label="分类编号">
                </el-table-column>
                <el-table-column prop="dishesTypeName" label="分类名称">
                </el-table-column>
                <el-table-column prop="dishesTypeDesc" label="分类描述">
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
            <el-table-column prop="dishesTypeId" label="分类编号" width="120">
            </el-table-column>
            <el-table-column prop="dishesTypeName" label="分类名称">
            </el-table-column>
            <el-table-column prop="dishesTypeDesc" label="分类描述">
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" width="380">
                <template slot-scope="scope">
                    <!-- 修改信息按钮 -->
                    <el-button type="primary" size="mini" @click="updateOpen(scope.row)"
                        icon="el-icon-edit">修改分类信息</el-button>

                    <!-- 删除信息按钮 -->
                    <el-popconfirm v-model="visible" title="确定要删除此分类吗？" @confirm='handleConfirm(scope.row)'
                        @cancel='visible = false'>
                        <el-button type="danger" size="mini" slot="reference" style="margin-left: 10px;"
                            icon="el-icon-delete-solid">删除分类</el-button>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>

        <!-- 添加弹窗 -->
        <el-dialog title="添加分类" :visible.sync="addFormVisible" :show-close="false">
            <el-form :model="addform">
                <el-form-item label="分类名称" :label-width="formLabelWidth">
                    <el-input v-model="addform.dishesTypeName" autocomplete="off" required maxlength="20"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="分类描述" :label-width="formLabelWidth">
                    <el-input v-model="addform.dishesTypeDesc" autocomplete="off" required maxlength="200"
                        show-word-limit type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quxiao()">取 消</el-button>
                <el-button type="primary" @click="addSubmit()">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 修改弹窗 -->
        <el-dialog title="修改分类信息" :visible.sync="updateVisible" :show-close="false">
            <el-form :model="form">
                <el-form-item label="分类名称" :label-width="formLabelWidth">
                    <el-input v-model="form.dishesTypeName" autocomplete="off" required maxlength="20"
                        show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="分类描述" :label-width="formLabelWidth">
                    <el-input v-model="form.dishesTypeDesc" autocomplete="off" required maxlength="200" show-word-limit
                        type="textarea"></el-input>
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
import { getDishesTypeList, deleteDishesType, addDishesType, updateDishesType, searchDishesType } from '@/api/DishesTypeAPI.js'
import htmlToExcel from '@/utils/htmlToExcel.js'
export default {
    name: 'InnovationSystemDishesType',

    data() {
        return {
            videoList: [],
            previewVisible: false, // 预览对话框
            previewVisible2: false, // 预览对话框
            //表格中选中的数据
            selectData: [],
            selectWindow: false,
            tableData: [],
            dishesTypeName: '',
            addFormVisible: false,
            visible: false,
            dialogFormVisible: false,
            formLabelWidth: '120px',
            dialogVisible: false,
            dialogVisible2: false,
            form: {
                dishesTypeId: '',
                dishesTypeName: '',
                dishesTypeDesc: ''
            },
            addform: {
                dishesTypeName: '',
                dishesTypeDesc: ''
            },
            updateVisible: false
        };
    },

    mounted() {

    },

    methods: {
        quxiao() {
            this.addFormVisible = false
            this.updateVisible = false
        },
        //导出
        exportExcel() {
            htmlToExcel.getExcel('#selectTable', '菜品分类信息')
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
        async updateSubmit() {
            let isEmpty = this.$infoRule(this.form)
            if (isEmpty == true) {
                this.$message.error("分类信息不完整!")
                return;
            }
            let { data: res } = await updateDishesType(this.form)
            if (res.success) {
                this.$message.success("菜品分类信息更新成功")
                this.tableData = res.result
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
            let { data: res } = await deleteDishesType(row.dishesTypeId)
            if (res.success) {
                this.$message.success("删除分类" + row.dishesTypeName + "成功")
                this.tableData = res.result
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
            let { data: res } = await addDishesType(this.addform)
            if (res.success) {
                this.$message.success("添加成功!")
                this.tableData = res.result
                this.addFormVisible = false
            } else {
                this.$message.error(res.msg)
            }
        },
        addNotice() {
            this.addform.dishesTypeName = ''
            this.addform.dishesTypeDesc = ''
            this.addFormVisible = true
        },
        async search() {
            if (this.dishesTypeName == '') {
                let { data: res } = await getDishesTypeList()
                this.tableData = res.result
            } else {
                let { data: res } = await searchDishesType(this.dishesTypeName)
                if (res.success) {
                    this.tableData = res.result
                    this.$message.success("搜索成功!")
                } else {
                    this.$message.error(res.msg)
                }
            }
        },
    },
    async created() {
        let { data: res } = await getDishesTypeList()
        this.tableData = res.result
    }
};
</script>

<style lang="less" scoped>
.preview-video {
    width: 100%;
    max-height: 300px;
    object-fit: cover;
    border-radius: 4px;
}

/* 视频缩略图样式 */
.video-thumbnail {
    width: 100%;
    height: 100%;
    object-fit: cover;
    position: relative;
}

/* 播放遮罩层 */
.video-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}

.el-icon-video-play {
    color: #fff;
    font-size: 40px;
}

/* 对话框视频容器 */
.el-dialog__body {
    padding: 20px;
    background: #000;
}

/* 上传按钮hover效果 */
.el-upload:hover {
    border-color: #409EFF;
}
</style>