<template>
    <div>
        <div class="main">
            <div class="boxList">
                <div class="box" style="background-color: #00c9a7;">
                    <div class="box1">{{ chartsInfo.totalPeople }}</div>
                    <div class="box2">系统总人数</div>
                </div>
                <div class="box" style="background-color: #00DDFF;">
                    <div class="box1">{{ chartsInfo.ordersTotal }}</div>
                    <div class="box2">订单总数</div>
                </div>
                <div class="box" style="background-color: #37A2FF;">
                    <div class="box1">{{ chartsInfo.dishesTotal }}</div>
                    <div class="box2">菜品统计</div>
                </div>
                <div class="box" style="background-color: #FF0087;">
                    <div class="box1">{{ chartsInfo.priceTotal + '￥' }}</div>
                    <div class="box2">销售金额</div>
                </div>
            </div>

            <!-- 中间图表 -->
            <div class="mid">
                <!-- 各类型服务数量 -->
                <div class="chart-container1">
                    <h2>各分类菜品统计</h2>
                    <div ref="salesChart" class="chart"></div>
                </div>

                <!-- 服务与订单 -->
                <div class="chart-container2">
                    <h2>半月销售额统计</h2>
                    <div ref="transactionChart" class="chart"></div>
                </div>
            </div>

            <!-- 新增用户/访问量 -->
            <div class="chart-container3">
                <h2 style="text-align: center;padding-top: 10px;">平台人员活跃度</h2>
                <div ref="userChart" class="chart"></div>
            </div>

        </div>

    </div>

</template>

<script>
import { getDataInfo } from '@/api/AdminAPI.js';
import * as echarts from 'echarts';
export default {
    name: 'stats',

    data() {
        return {
            declaredChartsInfo: '',
            chartsInfo: '',
            dateList: '',
            numberList: '',
            dateList2: '',
            numberList2: '',
            dateList3: '',
            numberList3: '',
            activityData: [],
            bookingTotal: '',
            orderTotal: '',
            ops: []
        };
    },
    methods: {
        // 渲染新增用户/访问量图表
        renderUserChart(data) {
            const chart = echarts.init(this.$refs.userChart);
            const categories = data.map(item => item.loginDate);
            const userLogin = data.map(item => item.userLogin);
            const adminLogin = data.map(item => item.adminLogin);

            const option = {
                color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087'],
                xAxis: {
                    type: 'category',
                    data: categories
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '用户活跃数',
                        data: userLogin,
                        type: 'line',
                        smooth: true,
                        itemStyle: { color: '#5470C6' },
                        stack: 'Total',
                        smooth: true,
                        lineStyle: {
                            width: 0
                        },
                        showSymbol: false,
                        areaStyle: {
                            opacity: 0.8,
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {
                                    offset: 0,
                                    color: 'rgb(128, 255, 165)'
                                },
                                {
                                    offset: 1,
                                    color: 'rgb(1, 191, 236)'
                                }
                            ])
                        },
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        name: '管理员活跃数',
                        data: adminLogin,
                        type: 'line',
                        stack: 'Total',
                        smooth: true,
                        lineStyle: {
                            width: 0
                        },
                        showSymbol: false,
                        areaStyle: {
                            opacity: 0.8,
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {
                                    offset: 0,
                                    color: 'rgb(0, 221, 255)'
                                },
                                {
                                    offset: 1,
                                    color: 'rgb(77, 119, 255)'
                                }
                            ])
                        },
                        emphasis: {
                            focus: 'series'
                        },
                    }
                ],
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                legend: {
                    data: ['用户活跃数', '管理员活跃数']
                },
            };
            chart.setOption(option);
        },

        // 各分类菜品数
        renderSalesChart() {
            const chart = echarts.init(this.$refs.salesChart);
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    bottom: 'auto',  // 可以根据需要调整或移除
                    left: '10%',     // 调整图例到左侧的位置
                    orient: 'vertical' // 将图例设置为垂直布局
                },
                series: [
                    {
                        name: '菜品数',
                        type: 'pie',
                        radius: ['35%', '70%'],
                        data: this.activityData
                    }
                ]
            };
            chart.setOption(option);
        },
        // 渲染预约与订单数
        renderTransactionChart() {
            // 初始化 ECharts 实例
            const chart = echarts.init(this.$refs.transactionChart);
            const orderTimes = this.ops.map(item => item.orderTime);
            const totalPrices = this.ops.map(item => item.totalPrice);
            const option = {
                // 增加全局网格布局配置 [3,6](@ref)
                grid: {
                    left: '8%',
                    right: '5%',
                    bottom: '15%',
                    top: '15%',
                    containLabel: true
                },
                // 设置透明背景 [3](@ref)
                backgroundColor: 'rgba(0,0,0,0)',
                tooltip: {
                    trigger: 'axis',
                    // 美化提示框样式 [4,6](@ref)
                    backgroundColor: 'rgba(50,50,50,0.9)',
                    borderColor: '#333',
                    textStyle: {
                        color: '#fff',
                        fontSize: 14
                    },
                    formatter: '{b}<br/>{a}: ¥{c}'
                },
                xAxis: {
                    type: 'category',
                    data: orderTimes,
                    // 优化坐标轴标签 [3,6](@ref)
                    axisLabel: {
                        color: '#666',
                        fontSize: 14,
                        rotate: 45 // 日期过长时旋转标签
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#999' // 坐标轴线颜色
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    // 隐藏轴线但保留刻度 [3](@ref)
                    axisLine: { show: false },
                    axisTick: { show: false },
                    splitLine: {
                        lineStyle: {
                            color: '#eee', // 浅灰色网格线
                            opacity: 0.6
                        }
                    }
                },
                series: [{
                    name: '金额',
                    type: 'bar',
                    data: totalPrices,
                    // 添加渐变效果 [3,5](@ref)
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: '#5470c6' },   // 顶部颜色
                            { offset: 1, color: '#91cc75' }    // 底部颜色
                        ]),
                        borderRadius: [8, 8, 0, 0] // 顶部圆角
                    },
                    // 优化交互效果 [6](@ref)
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowColor: 'rgba(0,0,0,0.3)'
                        }
                    },
                    barWidth: '30%', // 更紧凑的宽度
                    // 添加动画效果 [6](@ref)
                    animationDuration: 1500,
                    animationEasing: 'cubicOut'
                }]
            };
            chart.setOption(option);
        }
    },
    async mounted() {
        let { data: res } = await getDataInfo()
        this.chartsInfo = res.result
        this.orderTotal = res.result.orderTotal
        this.bookingTotal = res.result.bookingTotal
        this.declaredChartsInfo = res.result.declaredChartsInfo
        this.activityData = this.chartsInfo.serveList.map(item => ({
            name: item.dishesTypeName,
            value: item.count
        }));
        this.ops = res.result.list
        this.renderUserChart(res.result.loginChartsList);
        this.renderSalesChart();
        this.renderTransactionChart();
    },
    created() {


    }
};
</script>
<style>
body {
    background-color: #f7f8f7;
}

.boxList {
    width: 100%;
    height: 100px;
    display: flex;
    justify-content: space-between;
}

.boxList .box {
    background-color: #4aa588;
    width: 20%;
    height: 100%;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-repeat: no-repeat;
    /* 不平铺 */
    background-size: 120px 80%;
    /* 背景图片大小 */
    background-position: 95% center;
    /* 背景图片位置居中 */
}

.box1 {
    font-size: 35px;
    font-weight: 600;
    color: #f7f8f7;
    text-align: center;
}

.box2 {
    color: #f7f8f7;
    text-align: center;
}

.text {
    width: 300px;
    height: 100px;
    margin: 0 auto;
    text-align: center;
}

.dashboard {
    font-family: Arial, sans-serif;
}

.header {
    background-color: #409EFF;
    color: white;
    padding: 20px;
    text-align: center;
}

.content {
    display: flex;
}

.sidebar {
    width: 200px;
    background-color: #f0f0f0;
    padding: 20px;
}

.sidebar ul {
    list-style-type: none;
    padding: 0;
}

.sidebar li {
    margin: 15px 0;
    cursor: pointer;
}

.main {
    flex: 1;
}

.mid {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 20px;
}

.chart-container3 {
    width: 100%;
    height: 350px;
    border-radius: 20px;
    background-color: white;
}

.chart-container1 {
    width: 48%;
    height: 350px;
    border-radius: 20px;
    text-align: center;

    background-color: white;
}

.chart-container1 h2,
.chart-container2 h2 {
    margin-top: 10px;
}

.chart-container2 {
    width: 48%;
    height: 350px;
    border-radius: 20px;
    text-align: center;
    background-color: white;
}

.chart {
    width: 100%;
    height: 300px;
}
</style>