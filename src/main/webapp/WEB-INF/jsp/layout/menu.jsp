<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <%-- 중간메뉴 시작--%>
    <div id="sticky-anchor"></div>
    <div class="path" id="sticky">
        <div class="inner">
            <a href="#n" onclick="COMMON.fn_form_submit('topForm','/main/main.do');" class="home">메인으로</a>
            <ul class="navlist">
            
            <%-- 현재화면 메뉴id --%>
            <%-- <c:set var="nowMenuid1" value="${fn:substring(menu_code,0,3)}" />
            <c:set var="nowMenuid2" value="${fn:substring(menu_code,0,5)}" />
            <c:set var="nowMenuid3" value="${fn:substring(menu_code,0,7)}" />
            <c:set var="nowMenuid4" value="${menu_code}" /> --%>
            
            <c:set var="nowMenuid1" value="${menu_id_1}" />
            <c:set var="nowMenuid2" value="${menu_id_2}" />
            <c:set var="nowMenuid3" value="${menu_id_3}" />
            <c:set var="nowMenuid4" value="${menu_id_4}" />

            <%-- ########## 첫번째 메뉴 콤보 ########## --%>
            <li>
            <c:forEach var="list" items="${menuInfo}" varStatus="status">
            	<c:if test="${nowMenuid1 eq list.menuId1}">
                	<a href="#n">${list.menuNm1}</a>
                </c:if>
            </c:forEach>
            
                <ul class="navdep2">
                	<%--1 dept 메뉴 --%>                	                	
	            	<c:forEach var="list" items="${menuInfo}" varStatus="status">	            	
						
						<c:set var="recent" value="" />
	            		<c:if test="${nowMenuid1 eq list.menuId1}">
	            			<c:set var="recent" value="recent" />   <!-- 1 dept 현재페이지 class:recent -->
	            		</c:if>
	            		
	            		<li class="depon ${recent}">
							<a href="#n">${list.menuNm1}</a>
							
							<%-- 2 dept 메뉴 --%>
							<ul class="navdep3">
							<c:forEach var="list2" items="${menuInfo2}" varStatus="status">	                			
								<c:if test="${list.menuId1 eq list2.upMenuId2}">										

										<%-- 3 dept 메뉴 --%>
										<c:set var="recent2" value="" />
					            		<c:if test="${nowMenuid2 eq list2.menuId2}">
					            			<c:set var="recent2" value="recent" />   <!-- 2 dept 현재페이지 class:recent -->
					            		</c:if>
	            		
										<li class="depon ${recent2}">
											<a href="#n">${list2.menuNm2}</a> <%-- 2 dept 메뉴명 --%>
											<ul class="navdep3">

												<c:forEach var="list3" items="${menuInfo3}" varStatus="status">	                			
													<c:if test="${list2.menuId2 eq list3.upMenuId3}">														

														<%-- 4 dept 메뉴 --%>
														<c:set var="recent3" value="" />
									            		<c:if test="${nowMenuid3 eq list3.menuId3}">
									            			<c:set var="recent3" value="recent" />   <!-- 3 dept 현재페이지 class:recent -->
									            		</c:if>

														<c:set var="depon" value="navon2" />
														<c:if test="${list3.upMenuYn3 eq 'Y'}">
															<c:set var="depon" value="depon" />
														</c:if>

														<li class="${depon} ${recent3}">														
														    <a href="#n" onclick="COMMON.fn_menu_click('${list3.menuId3}','${list3.scrinYn}','${list3.roleYn}','${list3.url}','topForm');">${list3.menuNm3}</a>  <%-- 3 dept 메뉴명 --%>
							                            	<c:if test="${list3.upMenuYn3 eq 'Y'}">  <%-- 4 dept 메뉴가 있으면... --%>
							                            		<ul class="navdep3">
																	<c:forEach var="list4" items="${menuInfo4}" varStatus="status">	                			
																		<c:if test="${list3.menuId3 eq list4.upMenuId4}">
																			
																			<c:choose>
												                            	<c:when test="${nowMenuid4 eq list4.menuId4}">
												                            		<li class="navon2 recent">				    
																						<a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a>
																				    </li>	                            		
																			    </c:when>
																			    <c:otherwise>													    				    
																					<li><a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a></li>														    
																			    </c:otherwise>
																		    </c:choose>
																			
																		</c:if>
																	</c:forEach>														
																</ul>																	
														    </c:if>	
																														
														</li>
														
													</c:if>
												</c:forEach>
																									
												
											</ul>
										</li>
										
								</c:if>
							</c:forEach>
							</ul>
						</li>
	            		            			            	
	            	</c:forEach>	            		                
                </ul>
            </li>
            
            <!-- ########## 두번째 메뉴 콤보 ########## -->
            <li>
            <c:forEach var="list2" items="${menuInfo2}" varStatus="status">
            	<c:if test="${nowMenuid2 eq list2.menuId2}">
                	<a href="#n">${list2.menuNm2}</a>
                </c:if>
            </c:forEach>

			<%--1 dept 메뉴 --%>                	                	
           	<c:forEach var="list" items="${menuInfo}" varStatus="status">					
				<c:if test="${nowMenuid1 eq list.menuId1}">
				
					<%-- 2 dept 메뉴 --%>
					<ul class="navdep2">
					<c:forEach var="list2" items="${menuInfo2}" varStatus="status">		                		
						<c:if test="${list.menuId1 eq list2.upMenuId2}">										

								<%-- 3 dept 메뉴 --%>
								<c:set var="recent2" value="" />
			            		<c:if test="${nowMenuid2 eq list2.menuId2}">
			            			<c:set var="recent2" value="recent" />   <!-- 2 dept 현재페이지 class:recent -->
			            		</c:if>
           		
								<li class="depon ${recent2}">
									<a href="#n">${list2.menuNm2}</a> <%-- 2 dept 메뉴명 --%>
									<ul class="navdep3">

										<c:forEach var="list3" items="${menuInfo3}" varStatus="status">	                			
											<c:if test="${list2.menuId2 eq list3.upMenuId3}">														

												<%-- 4 dept 메뉴 --%>
												<c:set var="recent3" value="" />
							            		<c:if test="${nowMenuid3 eq list3.menuId3}">
							            			<c:set var="recent3" value="recent" />   <!-- 3 dept 현재페이지 class:recent -->
							            		</c:if>

												<c:set var="depon" value="navon2" />
												<c:if test="${list3.upMenuYn3 eq 'Y'}">
													<c:set var="depon" value="depon" />
												</c:if>

												<li class="${depon} ${recent3}">														

												    <a href="#n" onclick="COMMON.fn_menu_click('${list3.menuId3}','${list3.scrinYn}','${list3.roleYn}','${list3.url}','topForm');">${list3.menuNm3}</a>  <%-- 3 dept 메뉴명 --%>

					                            	<c:if test="${list3.upMenuYn3 eq 'Y'}">  <%-- 4 dept 메뉴가 있으면... --%>
					                            		<ul class="navdep3">
															<c:forEach var="list4" items="${menuInfo4}" varStatus="status">	                			
																<c:if test="${list3.menuId3 eq list4.upMenuId4}">
																	
																	<c:choose>
										                            	<c:when test="${nowMenuid4 eq list4.menuId4}">
										                            		<li class="navon2 recent">				    
																				<a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a>
																		    </li>	                            		
																	    </c:when>
																	    <c:otherwise>													    				    
																			<li><a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a></li>														    
																	    </c:otherwise>
																    </c:choose>
																	
																</c:if>
															</c:forEach>														
														</ul>																	
												    </c:if>	
																												
												</li>
												
											</c:if>
										</c:forEach>
																							
										
									</ul>
								</li>
								
						</c:if>
					</c:forEach>
					</ul>
				
				</c:if>				
			</c:forEach>
						
            </li>
            
            <!-- ########## 세번째 메뉴 콤보 ########## -->
            <li>
            <c:forEach var="list3" items="${menuInfo3}" varStatus="status">
            	<c:if test="${nowMenuid3 eq list3.menuId3}">
                	<a href="#n">${list3.menuNm3}</a>
                </c:if>
            </c:forEach>

						
 			<c:forEach var="list" items="${menuInfo}" varStatus="status">									
				<c:if test="${nowMenuid1 eq list.menuId1}">
				
					<c:forEach var="list2" items="${menuInfo2}" varStatus="status">	
						<c:if test="${nowMenuid2 eq list2.menuId2}">
					
						<ul class="navdep2">
	
								<c:forEach var="list3" items="${menuInfo3}" varStatus="status">	                			
									<c:if test="${list2.menuId2 eq list3.upMenuId3}">														
	
										<%-- 4 dept 메뉴 --%>
										<c:set var="recent3" value="" />
					            		<c:if test="${nowMenuid3 eq list3.menuId3}">
					            			<c:set var="recent3" value="recent" />   <!-- 3 dept 현재페이지 class:recent -->
					            		</c:if>
	
										<c:set var="depon" value="navon2" />
										<c:if test="${list3.upMenuYn3 eq 'Y'}">
											<c:set var="depon" value="depon" />
										</c:if>
	
										<li class="${depon} ${recent3}">														
	
										    <a href="#n" onclick="COMMON.fn_menu_click('${list3.menuId3}','${list3.scrinYn}','${list3.roleYn}','${list3.url}','topForm');">${list3.menuNm3}</a>  <%-- 3 dept 메뉴명 --%>
	
			                            	<c:if test="${list3.upMenuYn3 eq 'Y'}">  <%-- 4 dept 메뉴가 있으면... --%>
			                            		<ul class="navdep3">
													<c:forEach var="list4" items="${menuInfo4}" varStatus="status">	                			
														<c:if test="${list3.menuId3 eq list4.upMenuId4}">
															
                                                            <c:choose>
								                            	<c:when test="${nowMenuid4 eq list4.menuId4}">
								                            		<li class="navon2 recent">				    
																		<a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a>
																    </li>	                            		
															    </c:when>
															    <c:otherwise>													    				    
																	<li><a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a></li>														    
															    </c:otherwise>
														    </c:choose>
												    
														</c:if>
													</c:forEach>														
												</ul>																	
										    </c:if>	
																										
										</li>
										
									</c:if>
								</c:forEach>
								
							</ul>
						
						</c:if>						
					</c:forEach>
					
				</c:if>
			</c:forEach>
									
            </li>
            
            <!-- ########## 네번째 메뉴 콤보 ########## -->
            <li>
            <c:forEach var="list4" items="${menuInfo4}" varStatus="status">
            	<c:if test="${nowMenuid4 eq list4.menuId4}">
                	<a href="#n">${list4.menuNm4}</a>
                </c:if>
            </c:forEach>
						
 			<c:forEach var="list" items="${menuInfo}" varStatus="status">									
				<c:if test="${nowMenuid1 eq list.menuId1}">
				
					<c:forEach var="list2" items="${menuInfo2}" varStatus="status">	
						<c:if test="${nowMenuid2 eq list2.menuId2}">												
	
								<c:forEach var="list3" items="${menuInfo3}" varStatus="status">            			
									<c:if test="${nowMenuid3 eq list3.menuId3}">														

									    <%-- 4 dept 메뉴 --%>									    
	                                    <ul class="navdep2">	                                        
											<c:forEach var="list4" items="${menuInfo4}" varStatus="status">	                			
												<c:if test="${list3.menuId3 eq list4.upMenuId4}">

													<c:choose>
						                            	<c:when test="${nowMenuid4 eq list4.menuId4}">
						                            		<li class="navon2 recent">				    
																<a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a>
														    </li>	                            		
													    </c:when>
													    <c:otherwise>													    				    
															<li><a href="#n" onclick="COMMON.fn_menu_click('${list4.menuId4}','${list4.scrinYn}','${list4.roleYn}','${list4.url}','topForm');">${list4.menuNm4}</a></li>														    
													    </c:otherwise>
												    </c:choose>
													
												</c:if>
											</c:forEach>	
										</ul>
										
									</c:if>
								</c:forEach>
						
						</c:if>						
					</c:forEach>
					
				</c:if>
			  </c:forEach>
			
            </li>
            
            
            </ul>
            
        </div>
    
    </div>
    