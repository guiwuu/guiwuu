package model;

import java.io.Serializable;

/**
 * 分页对象.
 * 
 */
public class Page implements Serializable {

	
	private static final long serialVersionUID = 3927459723286328872L;
	private static int DEFAULT_PAGE_SIZE = 20;
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的显示记录数
	private Object data; // 当前页中存放的记
	private long totalCount; // 总记录数
	private int currentPageNo; // 当前页数 从1开始
	private long totalPageCount; // 总页数
	private int blockSize = 9; // 块区间大小 默认为9

	/**
	 * 构造函数
	 * 
	 * @param currentPageNo
	 *            当前页号
	 * @param totalSize
	 *            数据库中总记录条
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public Page(int currentPageNo, long totalSize, int pageSize, Object data) {
		this.pageSize = pageSize;
		this.currentPageNo = currentPageNo;
		this.totalCount = totalSize;
		this.data = data;
	}


	/**
	 * 构造函数
	 * 
	 * @param data
	 */
	public Page(Object data) {
		this.data = data;
	}

	public Page() {
	}

	/**
	 * 总记录数
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 总页数
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			totalPageCount = totalCount / pageSize;
		else
			totalPageCount = totalCount / pageSize + 1;
		return totalPageCount;
	}

	/**
	 * 取每页显示数据条数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取数据
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 当前页数
	 */
	public int getCurrentPageNo() {
		if (totalCount == 0)
			currentPageNo = 0;
		return currentPageNo;
	}

	/**
	 * 该页是否有下页
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上页
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 返回当前块区间号
	 * 
	 * @return
	 */
	public int getBlockNo() {
		if (currentPageNo % blockSize == 0)
			return currentPageNo / blockSize;
		else
			return currentPageNo / blockSize + 1;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public static int getStartOfPage(int curPageNum, int pageSize) {
		if (curPageNum < 1) {
			curPageNum = 1;
		}
		return (curPageNum - 1) * pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
}