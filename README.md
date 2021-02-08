# BidBuy Android

This is an android application which contains client side implementation of [bidbuy-server project](https://github.com/ybedirhanpak/bidbuy-server)

## How to install

You can find the apk file at `apk/bidbuy.apk` and install it either in your android phone or emulator.
It will try to connect server deployed at host `35.187.78.77`. 

## Run with local server

In case of this server is closed, you can connect the local server.
Download the server [bidbuy-server project](https://github.com/ybedirhanpak/bidbuy-server), and follow the run instructions.

### Modify Android Code

You need to modify this line in `com.yabepa.bidbuy.network.Client`:
``` 
private static final String SERVER_IP = "35.187.78.77";
------>
private static final String SERVER_IP = "192.168.1.X";
```

Where "192.168.1.X" is your computer's local ip.

You can find this ip with `ifconfig` command in MacOS/Linux, it will be under the `en0` section.
