package com.crawler.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    /*
    商品集合id
     */
    private String spu;
    /*
    商品最小品类id
     */
    private String sku;
    /*
    商品标题
     */
    private String title;
    /*
    商品价格
     */
    private Double price;
    /*
    商品图片
     */
    private String pic;
    /*
    商品详细地址
     */
    private String url;
    /*
    创建时间
     */
    private Date createTime;

    public Item(SearchItemInfo info) {
        this.spu = info.getSpu();
        this.sku = info.getSku();
        this.pic = info.getPic();
        this.url = info.getUrl();
        createTime = new Date();
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
