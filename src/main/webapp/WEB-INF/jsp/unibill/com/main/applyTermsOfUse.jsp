<%--
  Created by IntelliJ IDEA.
  User: myLoad
  Date: 2022-01-29
  Time: 오후 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6">
                        <h5 class="text-bold m-0">서비스 이용 약관</h5>
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
                            <!-- 약관내용  S -->
                            <div class="policy" >
                                <iframe frameborder="0" scrolling="auto" height="500px" width="100%" src="/main/applyTermsOfUsePolicy.do" name="I1">
                                    텍스트입력

                                </iframe>
                            </div>
                            <!-- 양관내용  E -->
                        </div>
                    </div>
                    <!-- /.card -->

                    <!-- Input addon -->

                    <!-- /.card -->
                    <!-- Horizontal Form -->

                    <!-- /.card -->

                </div>
                <!-- ./col -->

                <!-- ./col -->
            </div>
            <!-- /.row -->
            <!-- Main row -->

            <div class="agree" align="center">
                <form name="termsOfUse" id="termsOfUse" method="POST" action="/main/applySubscription.do">
                    <input type="radio" name="agree" value="Y"> 동의함 &nbsp;&nbsp;
                    <input type="radio" name="agree" value="N"> 동의안함
                </form>
            </div>
            <div class="text-center mb-4 mt-5">
                <a class="btn btn-dark btn-lg col-5 col-sm-2 font-weight-bold text-sm" onclick="history.back(-1)"><i class="fas fa-chevron-circle-left mr-2"></i>이 전</a>
                <a class="btn btn-danger btn-lg col-5 col-sm-2 font-weight-bold text-sm" onclick="goApply()">다 음<i class="fas fa-chevron-circle-right ml-2"></i></a>
            </div>

            <div class="row">
                <!-- Left col -->

                <!-- right col -->
            </div>
            <!-- /.row (main row) -->
            </div><!-- /.container-fluid -->
        </section>
        <!-- /.content -->
        <script>
            function goApply(){
            	if($('input:radio[name=agree]').val()=='Y'){
                    $('#termsOfUse').submit()
                }else{
            		alert('이용약관 "동의함" 선택 후후 신청이 가능합니다.')                }
            }
        </script>