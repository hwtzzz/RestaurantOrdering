<template>
    <div class="home-page">
        <!-- 轮播图 -->
        <section class="carousel">
            <div class="slides" :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
                <div class="slide" v-for="(slide, index) in slides" :key="index">
                    <img :src="slide.image" />
                    <div class="slide-content">
                        <h1>{{ slide.title }}</h1>
                        <p>{{ slide.description }}</p>
                    </div>
                </div>
            </div>
            <button class="prev" @click="prevSlide">&#10094;</button>
            <button class="next" @click="nextSlide">&#10095;</button>
        </section>

        <!-- 餐厅特色 -->
        <!-- <section class="features">
            <h2>————————— 餐厅特色 —————————</h2>
            <div class="feature-cards">
                <div class="feature-card" v-for="feature in features" :key="feature.id">
                    <img :src="feature.icon" :alt="feature.title" />
                    <h3>{{ feature.title }}</h3>
                    <p>{{ feature.description }}</p>
                </div>
            </div>
        </section> -->

        <!-- 推荐菜品 -->
        <section class="recommendations">
            <h2>———————— 推荐菜品 ————————</h2>
            <div class="cards">
                <div class="card" v-for="dish in dishes" :key="dish.id">
                    <img :src="dish.dishesImage" :alt="dish.dishesName" />
                    <h3>{{ dish.dishesName }}</h3>
                    <p>{{ dish.dishesDesc | truncate(100) }}</p>
                </div>
            </div>
        </section>

        <!-- 顾客评价 -->
        <section class="reviews">
            <h2>——————— 顾客评价 ———————</h2>
            <div class="review-cards">
                <div class="review-card" v-for="review in reviews" :key="review.id">
                    <img :src="review.avatar" :alt="review.name" />
                    <h3>{{ review.name }}</h3>
                    <p>{{ review.appraise }}</p>
                    <div class="rating">评分：{{ review.star }} / 5</div>
                </div>
            </div>
        </section>

    </div>
</template>
<script>
import { getIndexDishes } from '@/api/DishesAPI';
// import image1 from '@/assets/orderingBG.jpg'
// import image2 from '@/assets/orderingBG2.jpg'
import { getIndexBanner } from '@/api/BannerAPI';
import { getIndexAppraise } from '@/api/OrderAPI';
export default {
    name: 'HomePage',
    data() {
        return {
            currentSlide: 0,
            slides: [],
            // features: [
            //     {
            //         id: 1,
            //         icon: image1,
            //         title: '新鲜食材',
            //         description: '每日精选，确保食材新鲜',
            //     },
            //     {
            //         id: 2,
            //         icon: image1,
            //         title: '顶级厨师',
            //         description: '经验丰富，技艺精湛',
            //     },
            //     {
            //         id: 3,
            //         icon: image2,
            //         title: '舒适环境',
            //         description: '优雅装修，温馨氛围',
            //     }
            // ],
            dishes: [],
            reviews: [],
        };
    },
    methods: {
        prevSlide() {
            this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
        },
        nextSlide() {
            this.currentSlide = (this.currentSlide + 1) % this.slides.length;
        },
    },
    filters: {
        truncate(value, maxLength) {
            if (!value) return ''; // 如果值为空，返回空字符串
            if (value.length <= maxLength) return value; // 如果长度小于等于最大长度，直接返回
            return value.slice(0, maxLength) + '...'; // 截断文本并添加省略符号
        },
    },
    async mounted() {
        let { data: res } = await getIndexDishes()
        this.dishes = res.result
        let { data: r } = await getIndexBanner()
        this.slides = r.result
        let {data:a} = await getIndexAppraise()
        this.reviews = a.result
        setInterval(this.nextSlide, 5000); // 自动播放
    },
};
</script>
<style scoped>
/* 全局样式 */
.home-page {
    font-family: Arial, sans-serif;
    color: #333;
}

/* 轮播图 */
.carousel {
    position: relative;
    width: 100%;
    overflow: hidden;
}

.slides {
    display: flex;
    transition: transform 0.5s ease;
}

.slide {
    min-width: 100%;
    position: relative;
    border-bottom-left-radius: 100px;
}

.slide img {
    width: 100%;
    height: 400px;
    object-fit: cover;
    border-bottom-left-radius: 100px;
}

.slide-content {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    color: white;
}

.slide-content h1 {
    font-size: 3rem;
    margin-bottom: 10px;
}

.slide-content p {
    font-size: 1.5rem;
}

.prev,
.next {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
}

.prev {
    left: 10px;
}

.next {
    right: 10px;
}

/* 餐厅特色 */
.features {
    padding: 40px 20px;
    text-align: center;
}

.features h2 {
    font-size: 2rem;
    margin-bottom: 20px;
    color: #00d2fc;
}

.feature-cards {
    display: flex;
    justify-content: center;
    gap: 20px;
    flex-wrap: wrap;
}

.feature-card {
    width: 400px;
    padding: 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature-card:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.feature-card img {
    width: 400px;
    height: 200px;
    margin-bottom: 10px;
    border-radius: 10px;
}

.feature-card h3 {
    font-size: 1.5rem;
    margin-bottom: 10px;
}

.feature-card p {
    font-size: 1rem;
    color: #666;
}

/* 推荐菜品 */
.recommendations {
    padding: 40px 20px;
    text-align: center;
}

.recommendations h2 {
    font-size: 2rem;
    margin-bottom: 20px;
    color: #00d2fc;
    padding-bottom: 10px;
}

.cards {
    display: flex;
    justify-content: center;
    gap: 20px;
    flex-wrap: wrap;
}

.card {
    width: 400px;
    background: white;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.card img {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.card h3 {
    font-size: 1.5rem;
    margin: 10px 0;
}

.card p {
    font-size: 1rem;
    color: #666;
    padding: 0 10px 10px;
}

/* 顾客评价 */
.reviews {
    padding: 40px 20px;
    text-align: center;
}

.reviews h2 {
    font-size: 2rem;
    margin-bottom: 20px;
    color: #00d2fc;
    padding-bottom: 10px;
}

.review-cards {
    display: flex;
    justify-content: center;
    gap: 20px;
    flex-wrap: wrap;
}

.review-card {
    width: 300px;
    padding: 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.review-card:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.review-card img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.review-card h3 {
    font-size: 1.5rem;
    margin-bottom: 10px;
}

.review-card p {
    font-size: 1rem;
    color: #666;
    margin-bottom: 10px;
}

.rating {
    font-size: 1rem;
    color: #ff9800;
}
</style>