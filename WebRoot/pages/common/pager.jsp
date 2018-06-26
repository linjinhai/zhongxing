<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="common_system.jsp"%>
<script type="text/javascript">
	if ($("#pagerForm").length == 0) {
		$("body").wrapInner("<form id='pagerForm' action='${pager.url}' method='get'></form>");
	}
	function pager(pageNumber) {
		$("#pagerForm").prepend("<input type='hidden' name='pageNumber' value='" + pageNumber + "' />");
		$("#pagerForm").submit();
	}
</script>

<c:if test="${pager.pageCount >= 1}">
	<div class="row">
		<div class="col-md-6">
			<div class=" info">
				显示<strong> ${(pager.pageNumber-1)*pager.pageSize+1}-<c:if test="${pager.pageNumber != pager.pageCount}">${pager.pageNumber*pager.pageSize}</c:if><c:if test="${pager.pageNumber == pager.pageCount}">${pager.totalCount}</c:if></strong>条，共${pager.pageCount}页，共${pager.totalCount}条
			</div>
		</div>
		<div class="col-md-6">
			<div class="btn-group pull-right" role="group">
				<c:if test="${pager.pageNumber gt 1}">
					<button type="button" class="btn btn-default" onclick="javascript:pager(1);">首页</button>
				</c:if>
				<c:if test="${pager.pageNumber le 1}">
					<button type="button" class="btn btn-default disPage" onclick="">首页</button>
				</c:if>

				<c:if test="${pager.pageNumber gt 1}">
					<button type="button" class="btn btn-default" onclick="javascript:pager(${pager.pageNumber - 1});">上一页</button>
				</c:if>
				<c:if test="${pager.pageNumber le 1}">
					<button type="button" class="btn btn-default disPage">上一页</button>
				</c:if>

				<c:forEach begin="${pager.pageNumber}" end="${pager.pageCount}" var="key" varStatus="i">
					<c:if test="${pager.pageNumber eq 1}">
						<c:set var="pageCount" scope="page" value="7"/>
					</c:if>
					<c:if test="${pager.pageNumber eq 2}">
						<c:set var="pageCount" scope="page" value="6"/>
					</c:if>
					<c:if test="${pager.pageNumber ge 3}">
						<c:set var="pageCount" scope="page" value="5"/>
					</c:if>
					<c:if test="${pager.pageNumber gt 3}">
						<c:set var="pageCount" scope="page" value="4"/>
					</c:if>
					<c:if test="${i.count le pageCount}">
						<c:if test="${pager.pageNumber ne key}">
							<button type="button" class="btn btn-default" onclick="javascript:pager(${key});">${key}</button>
						</c:if>
					</c:if>
					<c:if test="${pager.pageNumber eq key}">
							<c:if test="${pager.pageNumber ge 2}">
								<c:if test="${pager.pageNumber ge 3}">
									<c:if test="${pager.pageNumber ge 4}">
										<button type="button" class="btn btn-default" onclick="javascript:pager(${key-3});">${key-3}</button>
									</c:if>
									<button type="button" class="btn btn-default" onclick="javascript:pager(${key-2});">${key-2}</button>
								</c:if>
								<button type="button" class="btn btn-default" onclick="javascript:pager(${key-1});">${key-1}</button>
							</c:if>
							<button type="button" class="btn btn-default currentPage" onclick="javascript:pager(${key});">${key}</button>
						</c:if>
				</c:forEach>

				<c:if test="${pager.pageNumber lt pager.pageCount}">
					<button type="button" class="btn btn-default" onclick="javascript:pager(${pager.pageNumber + 1});">下一页</button>
				</c:if>
				<c:if test="${pager.pageNumber ge pager.pageCount}">
					<button type="button" class="btn btn-default disPage">下一页</button>
				</c:if>

				<c:if test="${pager.pageNumber lt pager.pageCount}">
					<button type="button" class="btn btn-default" onclick="javascript:pager(${pager.pageCount});">尾页</button>
				</c:if>
				<c:if test="${pager.pageNumber ge pager.pageCount}">
					<button type="button" class="btn btn-default disPage">尾页</button>
				</c:if>
			</div>
		</div>
	</div>
</c:if>