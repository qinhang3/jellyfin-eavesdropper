# Jellyfin Eavesdropper
## The proxy for jellyfin dlna server and disable transcode
Jellyfin的DLNA服务目前返回的播放地址是

- /Videos/{itemId}/stream.{container}
- API: https://api.jellyfin.org/#operation/GetVideoStreamByContainer

在某些格式下会被转码作为流媒体返回，无法客户端解码和拖动进度条,在手机播放体验极差。

通过Spring Cloud Gateway构建一个正向代理，替换返回报文中的播放地址为

- /Items/{itemId}/Download
- API: https://api.jellyfin.org/#operation/GetDownload

从而在播放器中可以直接播放。

## 已知兼容的客户端
- ios nplayer 3.12.12.1
