# 蒼穹外賣練習
使用Spring Boot框架所建立的外賣訂購系統。

僅完成商家管理端。

## 使用技術
- 後端框架
  
    SpringBoot (3.1.2)
  
    mybatis

- 資料庫
  
    MySql
  
    Redis

- 前端框架
  
    Vue
  
    Uniapp


## 項目搭建
啟動nginx

導入sql

上傳文件服務

需要在yml配置文件中，填入AWS金鑰資料

帳戶:

admin

123456


## 模組劃分
|基礎資料模組|點餐業務模組|統計報表模組|
|----|----|----|
|員工管理|店鋪營業狀態設置|圖形報表|
|分類管理|客戶端登陸|EXCEL報表|
|菜品管理|緩存商品||
|套餐管理|購物車||
||客戶下單||
||訂單支付與管理||
||歷史訂單||
||訂單狀態定時處理||
||來單提醒與催單||


## 項目展示
![資料統計](/demo/資料統計.png)

![訂單管理](/demo/訂單管理.png)

![員工管理](/demo/員工管理.png)
