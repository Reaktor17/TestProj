package com.drg.testretrofit;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * RetrofitService
 */
public interface RetrofitService {

	/* sync */
	@GET("/traps")
	Trap getTrap(@Query("id") Integer id);

	@GET("/traps")
	List<Trap> getTraps();

	/* async */
	@GET("/traps")
	void getTrap(@Query("id") Integer id, Callback<Trap> callback);

	@GET("/traps")
	void getTraps(Callback<List<Trap>> callback);

//	@POST("/traps/add")
	@POST("/")
	void addTrap(@Query("trap") Trap trap, Callback<String> callback);
//	@POST("/")
//	void addTrap(@Body Trap trap, Callback<String> callback);
	@GET("/")
	void addTrapG(@Query("trap") Trap trap, Callback<String> callback);

//	@GET("/user/{id}/photo")
//	void getUserPhoto(@Path("id") int id, Callback<Photo> cb);
}
