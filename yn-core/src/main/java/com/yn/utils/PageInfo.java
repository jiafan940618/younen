package com.yn.utils;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;


/**
 * 通用分页类
 *
 * @param <T>
 */
public class PageInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前页
	 */
	private int pageIndex;
	
	/**
	 * 每页行数
	 */
	private int pageSize;
	
	/**
	 * 上一页
	 */
	
	private int prePage;
	
	/**
	 * 首页
	 */
	
	private int firstPage;
	
	/**
	 * 末页
	 */
	
	private int lastPage;
	
	
	/**
	 * 下一页
	 */
	
	private int nextPage;
	
	/**
	 * 当前页的数量
	 */
	private int size;
	
	/**
	 * 总记录数
	 */
	private long total;
	
	/**
	 * 总页数
	 */
	private int pages;
	
	/**
	 * 
	 */
	private String orderBy;
	/**
	 * 当前页面第一个元素在数据库中的行号
	 */
	private int startRow;
	/**
	 * 当前页面最后一个元素在数据库中的行号
	 */
	private int endRow;
	
	
	/**
	 * 结果集
	 */
	private List<T> list;
	/**
	 * 导航页码的第一页
	 */
	private int FirsttoPage;
	/**
	 * 导航页码的上一页
	 */
	private int PretoPage;
	/**
	 * 导航页码的下一页
	 */
	private int NexttoPage;
	/**
	 * 导航页码的最后一页
	 */
	private int LasttoPage;
	/**
	 * 是否有导航页码的第一页
	 */
	private boolean isFirstPage;
	/**
	 * 是否有导航页码的最后一页
	 */
	private boolean isLastPage;
	/**
	 * 是否有导航页码的上一页
	 */
	private boolean hasPreviousPage;
	/**
	 * 是否有导航页码的下一页
	 */
	private boolean hasNextPage;
	/**
	 * 导航页码数
	 */
	private int navigatePages;
	/**
	 * 所有的导航页码
	 */
	private int navigatepageNums[];
	
	public PageInfo()
	{
		isFirstPage = false;
		isLastPage = false;
		hasPreviousPage = false;
		hasNextPage = false;
	}

	public PageInfo(List<T> list)
	{
		this(list, 8);
	}

	public PageInfo(List<T> list, int navigatePages)
	{
		isFirstPage = false;
		isLastPage = false;
		hasPreviousPage = false;
		hasNextPage = false;
		if (list instanceof Page)
		{
			Page page = (Page)list;
			pageIndex = page.getPageNum();
			pageSize = page.getPageSize();
			orderBy = page.getOrderBy();
			pages = page.getPages();
			firstPage=1;
			lastPage=page.getPages();
			//上一页
			if(page.getPageNum()>0&&page.getPageNum()<=page.getPages()){
				prePage=page.getPageNum()-1;
			}else{
				prePage=1;
			}
			//下一页
			if(page.getPageNum()<page.getPages()){
				nextPage=page.getPageNum()+1;
			}else{
				nextPage=page.getPages();
			}
			
			this.list = page;
			size = page.size();
			total = page.getTotal();
			if (size == 0)
			{
				startRow = 0;
				endRow = 0;
			} else
			{
				startRow = page.getStartRow() + 1;
				endRow = (startRow - 1) + size;
			}
		} else
		if (list instanceof Collection)
		{
			pageIndex = 1;
			pageSize = list.size();
			pages = 1;
			this.list = list;
			size = list.size();
			total = list.size();
			startRow = 0;
			endRow = list.size() <= 0 ? 0 : list.size() - 1;
		}
		if (list instanceof Collection)
		{
			this.navigatePages = navigatePages;
			calcNavigatepageNums();
			calcPage();
			judgePageBoudary();
		}
	}

	private void calcNavigatepageNums()
	{
		if (pages <= navigatePages)
		{
			navigatepageNums = new int[pages];
			for (int i = 0; i < pages; i++)
				navigatepageNums[i] = i + 1;

		} else
		{
			navigatepageNums = new int[navigatePages];
			int startNum = pageIndex - navigatePages / 2;
			int endNum = pageIndex + navigatePages / 2;
			if (startNum < 1)
			{
				startNum = 1;
				for (int i = 0; i < navigatePages; i++)
					navigatepageNums[i] = startNum++;

			} else
			if (endNum > pages)
			{
				endNum = pages;
				for (int i = navigatePages - 1; i >= 0; i--)
					navigatepageNums[i] = endNum--;

			} else
			{
				for (int i = 0; i < navigatePages; i++)
					navigatepageNums[i] = startNum++;

			}
		}
	}

	private void calcPage()
	{
		if (navigatepageNums != null && navigatepageNums.length > 0)
		{
			FirsttoPage = navigatepageNums[0];
			LasttoPage = navigatepageNums[navigatepageNums.length - 1];
			if (pageIndex > 1)
				PretoPage = pageIndex - 1;
			if (pageIndex < pages)
				NexttoPage = pageIndex + 1;
		}
	}

	private void judgePageBoudary()
	{
		isFirstPage = pageIndex == 1;
		isLastPage = pageIndex == pages;
		hasPreviousPage = pageIndex > 1;
		hasNextPage = pageIndex < pages;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getFirsttoPage() {
		return FirsttoPage;
	}

	public void setFirsttoPage(int firsttoPage) {
		FirsttoPage = firsttoPage;
	}

	public int getPretoPage() {
		return PretoPage;
	}

	public void setPretoPage(int pretoPage) {
		PretoPage = pretoPage;
	}

	public int getNexttoPage() {
		return NexttoPage;
	}

	public void setNexttoPage(int nexttoPage) {
		NexttoPage = nexttoPage;
	}

	public int getLasttoPage() {
		return LasttoPage;
	}

	public void setLasttoPage(int lasttoPage) {
		LasttoPage = lasttoPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getFirstPage() {
		
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextpage) {
		this.nextPage = nextpage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	

	
}