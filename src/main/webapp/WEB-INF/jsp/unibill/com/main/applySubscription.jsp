<%--
  Created by IntelliJ IDEA.
  User: myLoad
  Date: 2022-01-29
  Time: 오후 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6">
                        <h5 class="text-bold m-0">청약신청</h5>
                    </div><!-- /.col -->
                    <!-- <div class="col-sm-6">
                       <ol class="breadcrumb float-sm-right">
                         <li class="breadcrumb-item"><a href="#">Home</a></li>
                         <li class="breadcrumb-item active">Dashboard v1</li>
                       </ol>
                     </div>--><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->
        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="card-body">

                        <div class="card card-danger card-outline">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>로그인 정보</h3>
                            </div>
                            <form class="form-horizontal">
                                <div class="card-body">
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">아이디</label>
                                        <div class="col-lg-10">
                                            <div class="row ml-0">
                                                <input type="text" class="form-control form-control-sm col-8 col-sm-3 " name="user_id" id="user_id" placeholder="id">
                                                <button id="check_id" type="button" class="btn btn-danger btn-sm small col-3 col-sm-2 float-left  ml-2" >중복확인</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">비밀번호</label>
                                        <div class="col-lg-10">
                                            <input type="email" class="form-control form-control-sm col-8 col-sm-3" id="inputEmail" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName2" class="col-lg-2 col-form-label small">비밀번호 확인</label>
                                        <div class="col-lg-10">
                                            <input type="text" class="form-control form-control-sm col-8 col-sm-3" id="inputName2" placeholder="Password">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <div class="small">
                                * 아이디 규칙 : 4~10자리<br>
                                * 비밀번호 규칙 : q영문+숫자+특수문자 8자리이상 또는 영문+숫자 10자리 이상 (대소문자 구분),연속된/반복된 숫자, 문자/아이디 포함 불가
                            </div>
                        </div>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>가입자 정보</h3>
                        </div>
                        <div class="card-body">
                            <div class="row mt-2">
                                <!---->
                                <nav class="col-12 mb-4">
                                    <div class="nav nav-tabs small" id="product-tab" role="tablist">
                                        <a class="nav-item nav-link active" id="product-desc-tab" data-toggle="tab" href="#product-desc" role="tab" aria-controls="product-desc" aria-selected="true">법인사업자</a>
                                        <a class="nav-item nav-link" id="product-comments-tab" data-toggle="tab" href="#product-comments" role="tab" aria-controls="product-comments" aria-selected="false">개인사업자</a>
                                        <a class="nav-item nav-link" id="product-rating-tab" data-toggle="tab" href="#product-rating" role="tab" aria-controls="product-rating" aria-selected="false">개인</a>
                                    </div>
                                </nav>

                                <div class="tab-content col-12 " id="nav-tabContent">
                                    <!--tab 1-->
                                    <div class="tab-pane fade show active" id="product-desc" role="tabpanel" aria-labelledby="product-desc-tab">
                                        <form class="form-horizontal">
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">법인상호명</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">사업자등록번호</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 float-left mr-1 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">대표자</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">업태</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">종목</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                                <div class="col-sm-10">
                                                    <div class="row">
                                                        <div class="col-sm-10">
                                                            <input class="pt-2"  type="checkbox">
                                                            <label class="small">사업장 주소와 동일</label>
                                                        </div>
                                                    </div>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                                    <span class="float-left m-1">@</span>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                                    <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                        <option>직접입력</option>
                                                        <option>option 2</option>
                                                        <option>option 3</option>
                                                        <option>option 4</option>
                                                        <option>option 5</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <!--tab 2-->
                                    <div class="tab-pane fade" id="product-comments" role="tabpanel" aria-labelledby="product-comments-tab">
                                        <form class="form-horizontal">
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">개인상호명</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">사업자등록번호</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 float-left mr-1 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">대표자</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">업태</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">종목</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                                <div class="col-sm-10">
                                                    <div class="row">
                                                        <div class="col-sm-10">
                                                            <input class="pt-2"  type="checkbox">
                                                            <label class="small">사업장 주소와 동일</label>
                                                        </div>
                                                    </div>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                                    <span class="float-left m-1">@</span>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                                    <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                        <option>직접입력</option>
                                                        <option>option 2</option>
                                                        <option>option 3</option>
                                                        <option>option 4</option>
                                                        <option>option 5</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <!--tab 3-->
                                    <div class="tab-pane fade" id="product-rating" role="tabpanel" aria-labelledby="product-rating-tab">
                                        <form class="form-horizontal">
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">성명</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputName" class="col-lg-2 col-form-label small">주민등록번호</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-5 col-sm-2 float-left mr-1 " id="inputName" placeholder="">
                                                    <span class="float-left mr-1 mt-1">-</span>
                                                    <input type="email" class="form-control form-control-sm col-5 col-sm-3 float-left mr-1" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                                <div class="col-sm-10">
                                                    <div class="row">
                                                        <div class="col-sm-10">
                                                            <input class="pt-2"  type="checkbox">
                                                            <label class="small">사업장 주소와 동일</label>
                                                        </div>
                                                    </div>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                    <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                                <div class="col-sm-10">
                                                    <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                                    <span class="float-left m-1">@</span>
                                                    <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                                    <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                        <option>직접입력</option>
                                                        <option>option 2</option>
                                                        <option>option 3</option>
                                                        <option>option 4</option>
                                                        <option>option 5</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <!--
                                <ul class="nav nav-tabs small mb-3 mt-3 font-weight-bold" id="custom-content-below-tab" role="tablist">
                                  <li class="nav-item">
                                    <a class="nav-link active " id="custom-content-below-home-tab" data-toggle="pill" href="#custom-content-below-home" role="tab" aria-controls="custom-content-below-home" aria-selected="true">법인사업자</a>
                                  </li>
                                  <li class="nav-item">
                                    <a class="nav-link" id="custom-content-below-profile-tab" data-toggle="pill" href="#custom-content-below-profile" role="tab" aria-controls="custom-content-below-profile" aria-selected="false">개인사업자</a>
                                  </li>
                                  <li class="nav-item">
                                    <a class="nav-link" id="custom-content-below-messages-tab" data-toggle="pill" href="#custom-content-below-messages" role="tab" aria-controls="custom-content-below-messages" aria-selected="false">개인</a>
                                  </li>
                                </ul>
                                <div class="tab-content" id="custom-content-below-tabContent">
                                  <div class="tab-pane fade show active" id="custom-content-below-home" role="tabpanel" aria-labelledby="custom-content-below-home-tab">
                                      <form class="form-horizontal">
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">법인상호명</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 " id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">사업자등록번호</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 float-left mr-1 " id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">대표자</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">업태</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">종목</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                          </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                            <div class="col-sm-10">
                                                <div class="row">
                                                  <div class="col-sm-10">
                                                  <input class="pt-2"  type="checkbox">
                                                  <label class="small">사업장 주소와 동일</label>
                                                  </div>
                                                </div>
                                                <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                            <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                                <span class="float-left m-1">@</span>
                                                <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                                <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                    <option>직접입력</option>
                                                    <option>option 2</option>
                                                    <option>option 3</option>
                                                    <option>option 4</option>
                                                    <option>option 5</option>
                                                  </select>
                                            </div>
                                          </div>
                                        </form>
                                  </div>
                                  <div class="tab-pane fade" id="custom-content-below-profile" role="tabpanel" aria-labelledby="custom-content-below-profile-tab">
                                      <form class="form-horizontal">
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">개인상호명</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 " id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">사업자등록번호</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-lg-3 col-sm-4 float-left mr-1 " id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">대표자</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">업태</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                              <label for="inputName" class="col-lg-2 col-form-label small">종목</label>
                                              <div class="col-sm-10">
                                                <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                              </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                          </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                            <div class="col-sm-10">
                                                <div class="row">
                                                  <div class="col-sm-10">
                                                  <input class="pt-2"  type="checkbox">
                                                  <label class="small">사업장 주소와 동일</label>
                                                  </div>
                                                </div>
                                                <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                              <span class="float-left m-1">@</span>
                                              <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                              <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                  <option>직접입력</option>
                                                  <option>option 2</option>
                                                  <option>option 3</option>
                                                  <option>option 4</option>
                                                  <option>option 5</option>
                                                </select>
                                          </div>
                                          </div>
                                        </form>
                                  </div>
                                  <div class="tab-pane fade" id="custom-content-below-messages" role="tabpanel" aria-labelledby="custom-content-below-messages-tab">
                                      <form class="form-horizontal">
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">성명</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputName" class="col-lg-2 col-form-label small">주민등록번호</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-5 col-sm-2 float-left mr-1 " id="inputName" placeholder="">
                                              <span class="float-left mr-1 mt-1">-</span>
                                              <input type="email" class="form-control form-control-sm col-5 col-sm-3 float-left mr-1" id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">사업장 주소</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                              <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                          </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">청구지 주소</label>
                                            <div class="col-sm-10">
                                                <div class="row">
                                                  <div class="col-sm-10">
                                                  <input class="pt-2"  type="checkbox">
                                                  <label class="small">사업장 주소와 동일</label>
                                                  </div>
                                                </div>
                                                <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <button type="submit" class="btn btn-secondary btn-sm small float-left mr-1 mb-1">우편번호찾기</button>
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left mr-1 mb-1" id="inputName" placeholder="">
                                                <input type="email" class="form-control form-control-sm col-sm-4 float-left" id="inputName" placeholder="">
                                            </div>
                                          </div>
                                          <div class="form-group row">
                                            <label for="inputEmail" class="col-lg-2 col-form-label small">세금계산서 발행 이메일</label>
                                            <div class="col-sm-10">
                                              <input type="email" class="form-control form-control-sm col-4 col-sm-3 float-left " id="inputName" placeholder="">
                                              <span class="float-left m-1">@</span>
                                              <input type="email" class="form-control form-control-sm col-3 col-sm-3 float-left  mr-1 mb-1" id="inputName" placeholder="">
                                              <select class="form-control form-control-sm col-4 col-sm-2 float-lg-left small">
                                                  <option>직접입력</option>
                                                  <option>option 2</option>
                                                  <option>option 3</option>
                                                  <option>option 4</option>
                                                  <option>option 5</option>
                                                </select>
                                          </div>
                                          </div>
                                        </form>
                                  </div>
                                </div>
                                -->
                            </div>
                        </div>
                        <!-- /.card -->
                        <div class="card-footer">
                            <div class="small m-2">
                                * 가입자 정보는 세금계산서 발행 정보로 사용됩니다.<br>
                                * 항목별 제한 길이를 초과할 경우 뒷자리는 자동삭제 됩니다. (한글 : 2Byte, 영문/숫자 : 1Byte)
                            </div>
                        </div>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>신청 정보</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form>
                            <div class="card-body">
                                <form class="form-horizontal">
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">매장위치</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-12 col-sm-2 float-left small mr-1 mb-1">
                                                <option>선택</option>
                                                <option>본점</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <select class="form-control form-control-sm col-12 col-sm-2 float-left small mr-1 mb-1">
                                                <option>선택안함</option>
                                                <option>신관</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <select class="form-control form-control-sm col-8 col-sm-2 float-left small ">
                                                <option>B5</option>
                                                <option>option 2</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <span class="col-sm-1 small">층</span>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">브랜드명</label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 float-left mr-1 " id="inputName" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">신청구분</label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control form-control-sm col-lg-2 col-sm-3 " id="inputName" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">내선번호</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-3 col-sm-2 float-left small mr-1">
                                                <option>010</option>
                                                <option>option 2</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <span class="float-left mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 " id="inputName" placeholder="">
                                            <span class="float-left mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1" id="inputName" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">개통요청일 (명의변경일)</label>
                                        <div class="col-sm-10 text-xs">
                                            <div class="input-group date col-5 col-sm-2 float-left p-0" id="reservationdate" data-target-input="nearest">
                                                <input type="text" class=" form-control  form-control-sm datetimepicker-input" data-target="#reservationdate">
                                                <div class="input-group-append" data-target="#reservationdate" data-toggle="datetimepicker">
                                                    <div class="input-group-text "><i class="far fa-calendar-alt"></i></div>
                                                </div>
                                            </div>
                                            <div class="float-left ml-1 mr-1 mt-1">-</div>
                                            <div class="input-group date col-5 col-sm-2 float-left p-0" id="reservationdate1" data-target-input="nearest">
                                                <input type="text" class="form-control form-control-sm datetimepicker-input" data-target="#reservationdate1">
                                                <div class="input-group-append" data-target="#reservationdate1" data-toggle="datetimepicker">
                                                    <div class="input-group-text"><i class="far fa-calendar-alt"></i></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">신청자명</label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control form-control-sm col-10 col-sm-2 float-left mr-1 " id="inputName" placeholder="Name">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">연락처</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-3 col-sm-2 float-left small mr-1">
                                                <option>010</option>
                                                <option>option 2</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <span class="float-left  mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 " id="inputName" placeholder="">
                                            <span class="float-left  mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1" id="inputName" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">전화 신청 수</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-3 col-sm-2 float-left small mr-1">
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                            <div class="">
                                                <span class="small mr-5">회선</span>
                                                <input class="form-check-input " type="checkbox">
                                                <label class="form-check-label small">링고(통화연결음) 사용여부 </label>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">인터넷 신청 수</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-3 col-sm-2 float-left small mr-1">
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                            <div class="">
                                                <span class="small mr-5">회선</span>
                                                <input class="form-check-input" type="checkbox">
                                                <label class="form-check-label small">VPN 사용여부 </label>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <!-- /.card-body -->
                        </form>
                        <div class="card-footer">
                            <div class="small m-2">
                                * VPN이란?  가상사설망으로 VPN으로 회사 시스템에 접속해야 하는 경우에만 사용에 체크해주세요.
                            </div>
                        </div>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>보증금 납부 정보 등록</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form >
                            <div class="card-body">
                                <form class="form-horizontal">
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">가상계좌</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-lg-2 col-sm-3 float-left small mr-1">
                                                <option>은행선택</option>
                                                <option>가상계좌</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </form>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>납부 정보</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form >
                            <div class="card-body">
                                <form class="form-horizontal">
                                    <div class="form-group row">
                                        <label for="inputEmail" class="col-lg-2 col-form-label small">납부 방식 선택</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-lg-2 col-sm-3 float-left small mr-1">
                                                <option>자동이체</option>
                                                <option>가상계좌</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputName" class="col-lg-2 col-form-label small">계산서 담당자 연락처</label>
                                        <div class="col-sm-10">
                                            <select class="form-control form-control-sm col-3 col-sm-2 float-left small mr-1">
                                                <option>010</option>
                                                <option>option 2</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                            <span class="float-left mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1 " id="inputName" placeholder="">
                                            <span class="float-left mr-1 mt-1">-</span>
                                            <input type="email" class="form-control form-control-sm col-3 col-sm-2 float-left mr-1" id="inputName" placeholder="">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </form>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>비고</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form class="form-horizontal">
                            <div class="card-body">
                                <div class="row">
                                    <div class=" col-12">
                                        <textarea class="form-control form-control-sm mb-1 " id="exampleInputPassword1" placeholder="" rows="2"></textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>고유식별정보 수집 및 이용 동의</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form class="form-horizontal">
                            <div class="card-body">
                                <div class="row">
                                    <div class=" col-12 small pb-1 pt-1">
                                        <dl>
                                            <dt>정보의 수집·이용 목적 </dt>
                                            <dd>- 세금계산서 발행 (근거법령: 부가가치세법 제32조 제1항 제2호) </dd>
                                        </dl>
                                        <dl>
                                            <dt>수집하는 정보의 항목</dt>
                                            <dd>- 주민등록번호, 외국인등록번호</dd>
                                        </dl>
                                        <dl>
                                            <dt>정보의 보유 및 이용기간</dt>
                                            <dd>- 서비스 이용 종료시까지</br>
                                                - 법령에 정하는 경우에는 해당 기간까지 보유</dd>
                                            </dd>
                                        </dl>
                                        <div>신청자는 개인정보를 제공하는 것을 거부할 권리가 있으며, 거부 시 세금계산서 정보 불충분으로 청약신청이 불가 할 수 있습니다.</div>
                                    </div>
                                </div>

                            </div>
                        </form>
                        <div class="card-footer">
                            <div class="float-right text-sm">
                                <input type="checkbox"> <label class="m-0">동의합니다.</label>
                            </div>
                        </div>
                    </div>
                    <div class="card card-danger card-outline">
                        <div class="card-header">
                            <h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>유의 사항</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form class="form-horizontal">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="">

                                            <table class="small">
                                                <tr>
                                                    <td colspan="2">
                                                        <div class="custom-control custom-checkbox  pb-2">
                                                            <input class="custom-control-input custom-control-input-danger" type="checkbox" id="customCheckbox4" checked="">
                                                            <label for="customCheckbox4 " class="custom-control-label">확인 후 체크해 주시기 바랍니다.</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>1. 가입비 【 3만원 (신규 전화 1회선 당, VAT별도) 】 - 후불: 요금 합산 청구, 반환되지 않음  </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>2. 명의변경 시 명변확정일 기준으로 일할계산되어 청구되며, 청구 후 수정불가 </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>3. 해지, 명의변경, 퇴점 등 변동 사항 발생 시에는 즉시 변경 신청을 해야하며, 신청 지연으로 인한 불이익(요금,타인사용 등)에 대해서는 당사의 책임이 없음  </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>4. 인터넷서비스용 임대장비 분실 시 【 모뎀 : 65,000원, VAT별도 】 【 어댑터 : 10,000원, VAT별도 】을 실비 청구함 </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>5. 전화/FAX, 인터넷 이전 설치 시 【 전화1회선,FAX 5,000원 】 【 인터넷 5,000원 】 【 전화+인터넷 5,000원 】(VAT별도) 을 익월 요금에 합산 청구함  </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>6. 전자세금계산서 확인 : eCtax 사이트 (www.ectax.co.kr)에서 확인 가능  </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td><span class="ml-1"></span>- 요금 청구 : 사용월 기준 익월 23일자 전자세금계산서 발행 </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td><span class="ml-1"></span>- 세금계산서 발행월 말일까지 요금을 납부하여야 하며 익월 10일까지 미입금시 서비스 정지가 되며, 서비스 정지 후 30일이내 미입금시 자동해지 처리됩니다.</td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>7. (개인,개인사업자 해당) 보증금 【 전화 1회선 당 10만원, 인터넷 해당 없음) 】- 보증금 입금 확인 후 개통 진행 (추후 반환됨) </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>8. (개인,개인사업자 해당) 보증금 입금 안내 (신한은행, 140-006-723323,예금주 : 신세계아이앤씨) 입금시 꼭 입금자명은 "보증금+성명"로 기재해야 확인 가능(보증금 별도 입금)  </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>9. (개인,개인사업자 해당) 요금납부는 CMS 자동이체만 가능 </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td>10. 입금한 보증금은 해지 시 [공지 사항] 참고하여 직접 환불 신청해야 하며, 청구 요금 전액 정산 완료 후 환불 진행</td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td><span class="ml-1"></span>- [공지사항]- 보증금 환불방법 및 (일정)절차안내 </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="checkbox" name="read" id="read" class=" mr-2 mt-1"></td>
                                                    <td><span class="ml-1"></span>- 환불시기 : 해지 월 사용요금 포함 익월 23일경 청구 및 청구월 말일에 출금, 미납 전액 납부 확인 후 환불 진행</td>
                                                </tr>
                                            </table>


                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="card-footer">
                            <div class="small m-2">
                                ※ 신청인은 위 사항을 숙지하고, 이에 귀사에 전화 또는 인터넷 서비스 가입/변경을 신청합니다.<br>
                                <div class="text-danger font-weight-bold">※ 휴일 및 법정 공휴일 보증금 입금시, 다음 영업일 오후 1시 이후에 입금확인 후 설치 가능 합니다. 양해 부탁드립니다.</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="text-center mb-5">
                <a class="btn btn-danger btn-lg col-10 col-sm-4 font-weight-bold">신청하기</a>
            </div>
        </section>
        <!-- /.content -->
