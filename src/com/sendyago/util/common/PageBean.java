package com.sendyago.util.common;



import java.util.List;
import java.util.Map;

public class PageBean
{
  private List list;
  private int allRow;
  private int totalPage;
  private int currentPage;
  private int pageSize;
  private boolean isFirstPage;
  private boolean isLastPage;
  private boolean hasPreviousPage;
  private boolean hasNextPage;
  private Map paramMap;

  public List getList()
  {
    return this.list;
  }
  public void setList(List list) {
    this.list = list;
  }
  public int getAllRow() {
    return this.allRow;
  }
  public void setAllRow(int allRow) {
    this.allRow = allRow;
  }
  public int getTotalPage() {
    return this.totalPage;
  }
  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
  public int getCurrentPage() {
    return this.currentPage;
  }
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }
  public int getPageSize() {
    return this.pageSize;
  }
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void init()
  {
    this.isFirstPage = isFirstPage();
    this.isLastPage = isLastPage();
    this.hasPreviousPage = isHasPreviousPage();
    this.hasNextPage = isHasNextPage();
  }

  public boolean isFirstPage()
  {
    return this.currentPage == 1;
  }
  public boolean isLastPage() {
    return this.currentPage == this.totalPage;
  }
  public boolean isHasPreviousPage() {
    return this.currentPage != 1;
  }
  public boolean isHasNextPage() {
    return this.currentPage != this.totalPage;
  }

  public static int countTotalPage(int pageSize, int allRow)
  {
    int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1;
    return totalPage;
  }

  public static int countOffset(int pageSize, int currentPage)
  {
    int offset = pageSize * (currentPage - 1);
    return offset;
  }

  public static int countCurrentPage(int page)
  {
    int curPage = page == 0 ? 1 : page;
    return curPage;
  }
  public Map getParamMap() {
    return this.paramMap;
  }
  public void setParamMap(Map paramMap) {
    this.paramMap = paramMap;
  }
}