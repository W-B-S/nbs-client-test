@echo off
set currentPath=%cd%
set "ts=%time: =0%"
set beginTime=%date:~0,4%-%date:~5,2%-%date:~8,2% %ts:~0,2%:%ts:~3,2%:%ts:~6,2%
set logDate=%date:~0,4%-%date:~5,2%-%date:~8,2%
set logTime=%ts:~0,2%%ts:~3,2%
set logfile=%currentPath%\%logDate%.%logTime%.ipfs.log
set INTERVAL=6
rem 运行约20分钟
set maxTime=5
set num=0
@echo off
echo %logfile%
echo ------------------- BEGIN : %beginTime% ----------------------------------- >> %logfile%
echo GOPATH: %GOPATH% >> %logfile%
echo "每隔 %INTERVAL% 秒检测 swarm peers" >> %logfile%
echo "当前 IPFS Node: " >> %logfile%
call ipfs id >> %logfile%
set params=%logfile%
echo "IPFS Starting........" >>  %logfile%
echo 启动时间 : %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2% %time% >> %logfile%
call ipfs.starter.bat
echo "IPFS START Success." >>  %logfile%
@ping -n 10 127.1 >nul
echo ************************************************************************************** >> %logfile%

@echo off
goto swarmpeers
pause

rem 启动监测
:swarmpeers
set /a num+=1

echo ****** The %num% Times ******
echo --------%num% [ %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2% %time%  ] --------------------------- >> %logfile%
@echo off
ipfs swarm peers >> %logfile%
@echo off
if %num% equ %maxTime% goto shutdown
@ping -n %INTERVAL% 127.1 >nul
goto swarmpeers

rem shutdown 
:shutdown
ipfs shutdown
exit

