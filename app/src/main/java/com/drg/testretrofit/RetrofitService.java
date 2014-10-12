package com.drg.testretrofit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
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

//	@GET("/user/{id}/photo")
//	void getUserPhoto(@Path("id") int id, Callback<Photo> cb);
}
