package com.guiwuu.swissknife.converter.cvs;

/**
 * For test
 *
 * @author daijun
 * @sine 2012-07-21
 */
public class OrderTestCase {

    private int id;

    private boolean available;

    private long timestamp;

    private String productName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
