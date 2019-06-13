package com.clx.clxdash;

import com.clx.clxdash.Netty.NettyServerListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClxdashApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClxdashApplication.class, args);
	}



	@Override
	public void run(String... String){


		NettyServerListener listener = new NettyServerListener();



		while(true){

			listener.start();
		}

	}

}
