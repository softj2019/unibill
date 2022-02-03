 
select * from fwmenu where menu_id like  'M0710%'; 
select * from fwscrin where scrin_id  like  'M0710%'; 
select * from fwmenuscrin where menu_id like  'M0710%'; 
select * from fwrolemenu where menu_id  like  'M0710%'; 
select * from fwform where scrin_id like  'M0710%'; 



-- 화면명 변경
update fwmenu set menu_nm=replace(menu_nm, '연동','종량제')  where menu_id like 'M0710%';
update fwscrin set SCRIN_NM=replace(SCRIN_NM, '연동','종량제')  where SCRIN_id like 'M0710%';

 -- menu 변경
update fwmenu set menu_id=replace(menu_id, 'M0710','M0406'), up_menu_id=replace(up_menu_id, 'M07','M04')  where menu_id like 'M07%' and `DEPTH`=2;
update fwmenu set menu_id=replace(menu_id, 'M0710','M0406'), up_menu_id=replace(up_menu_id, 'M0710','M0406')  where menu_id like 'M07%' and `DEPTH`=3;
update fwscrin set scrin_id=replace(scrin_id, 'M0710','M0406')  where scrin_id like 'M0710%'  ;
update fwmenuscrin set menu_id=replace(menu_id, 'M0710','M0406'), scrin_id=replace(scrin_id, 'M0710','M0406')  where scrin_id like 'M0710%'  ;
update fwrolemenu set menu_id=replace(menu_id, 'M0710','M0406'), scrin_id=replace(scrin_id, 'M0710','M0406')  where scrin_id like 'M0710%'  ;
update fwform set   scrin_id=replace(scrin_id, 'M0710','M0406')  where scrin_id like 'M0710%'  ;



-- update fwmenu set   up_menu_id=substr(menu_id,1,5) where menu_id like 'M07%' and `DEPTH`=3;
-- update fwmenu set   up_menu_id=substr(menu_id,1,5) where menu_id like 'M0406%' and `DEPTH`=3;




-- 화면 생성
-- 유사화면 선정 자료 가져오기
drop table aa;
create table aa as select * from fwform where scrin_id='M071007' ;  
select * from aa;
-- 신규 생성 화면으로 변경
update aa set scrin_id='M071008', ubseq= f_getsn('','ubseq'); 
insert into fwform select * from aa;

-- 편집
select *  from fwform where SCRIN_ID in ('M071008' )  order by DTL_ROW_POS, DTL_COL_POS;

select *  from fwobj  where obj_id in ('gugan_rate_id', 'gugan_dc_id', 'cust_id','rate_type','unit_type') ;
select *  from fwobj  where obj_id in ('key_id', 'maxcost') ; 



