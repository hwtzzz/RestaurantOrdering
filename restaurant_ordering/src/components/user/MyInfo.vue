<template>
    <div class="form-container">
        <el-card class="form-card" shadow="hover">
            <div slot="header" class="card-header">
                <span class="title">个人信息修改</span>
                <el-button type="primary" size="small" @click="submitForm" :loading="submitting">
                    保存修改
                </el-button>
            </div>

            <el-form :model="form" :rules="rules" ref="userForm" label-position="top">

                <!-- 真实姓名 -->
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入真实姓名" maxlength="10" show-word-limit>
                        <template slot="prefix">
                            <i class="el-icon-edit"></i>
                        </template>
                    </el-input>
                </el-form-item>

                <!-- 联系电话 -->
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入联系电话" type="number" maxlength="11">
                        <template slot="prefix">
                            <i class="el-icon-mobile-phone"></i>
                        </template>
                    </el-input>
                </el-form-item>

                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="form.sex" placeholder="请选择性别" required>
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>

            </el-form>
        </el-card>
        <!-- 修改个人信息改为用户个人 -->
        <el-card class="updateMyAvatar">
            <el-upload action="http://127.0.0.1:8888/api/user/updateMyAvatar" list-type="picture-card"
                :on-success="setUrl" :limit="1" :data="uploadAvatarData" ref='upload' :before-upload="beforeUpload">
                <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="form.avatar" alt="">
            </el-dialog>
        </el-card>

    </div>
</template>

<script>
import { updateMyInfo } from '@/api/UserAPI';
export default {
    name: 'InnovationSystemUpdateMyInfo',
    data() {
        // 自定义验证规则
        const validatePhone = (rule, value, callback) => {
            if (value && !/^1[3-9]\d{9}$/.test(value)) {
                callback(new Error('请输入有效的手机号码'))
            } else {
                callback()
            }
        }
        return {
            options: [
                {
                    value: 1,
                    label: '男'
                },
                {
                    value: 2,
                    label: '女'
                }],
            submitting: false,
            form: {
                userId: '',
                username: '',
                password: '',
                name: '',
                phone: '',
                avatar: '',
                sex: ''
            },
            dialogVisible: false,
            rules: {
                name: [
                    { required: true, message: '真实姓名不能为空', trigger: 'blur' },
                    { min: 2, max: 10, message: '长度在2到10个字符', trigger: 'blur' }
                ],
                phone: [
                    { validator: validatePhone, trigger: 'blur' }
                ],
            }
        }
    },

    mounted() {

    },
    computed: {
        uploadAvatarData() {
            return {
                userId: this.form.userId
            }
        }
    },
    methods: {
        // 提交表单
        submitForm() {
            this.$refs.userForm.validate(async valid => {
                if (valid) {
                    this.submitting = true
                    let isEmpty = this.$infoRule(this.form)
                    if (isEmpty == true) {
                        this.$message.error("信息不完整!")
                        this.submitting = false
                        return;
                    }
                    if (this.form.age < 0 || this.form.age > 100) {
                        this.$message.error("年龄不符合!")
                        this.submitting = false
                        return;
                    }
                    let { data: res } = await updateMyInfo(this.form)
                    if (res.success) {
                        localStorage.setItem("user", JSON.stringify(this.form));
                        this.$message.success("信息修改成功!")
                        this.submitting = false
                    } else {
                        this.submitting = false
                        this.$message.error(res.msg)
                    }
                }
            })
        },
        beforeUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 10;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 10MB!');
            }
            return isJPG && isLt2M;
        },
        handleRemove(file, fileList) {
            this.form.avatar = ''
        },
        setUrl(response, file, fileList) {
            this.form.avatar = response
            this.$message.success("头像已修改!")
            localStorage.setItem("user", JSON.stringify(this.form));
        },
        handlePictureCardPreview(file) {
            this.form.avatar = file;
        },
    },
    created() {
        this.form = JSON.parse(localStorage.getItem("user"))
    }
};
</script>
<style lang="less" scoped>
.form-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;

    .form-card {
        border-radius: 8px;

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;

            .title {
                font-size: 18px;
                font-weight: 500;
                color: #303133;
            }
        }

        .el-form-item {
            margin-bottom: 22px;

            &::v-deep .el-form-item__label {
                padding-bottom: 8px;
                font-weight: 500;
                color: #606266;
            }
        }

        .el-input__prefix {
            display: flex;
            align-items: center;
            padding: 0 8px;

            i {
                font-size: 16px;
                color: #c0c4cc;
            }
        }
    }
}

.updateAvatar {
    margin: 10px auto;
    width: 800px;
    height: 200px;
}
</style>