SET NET_XNGO_HOME=%~dp0
CALL ant -f %NET_XNGO_HOME%\build.xml

cp %NET_XNGO_HOME%\releases\net.xngo.utils.jar %NET_XNGO_HOME%\..\..\FilesHub\FilesHub\lib

pause
