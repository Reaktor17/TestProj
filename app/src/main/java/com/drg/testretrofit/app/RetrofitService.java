package com.drg.testretrofit.app;


import com.drg.testretrofit.models.ServerResp;
import com.drg.testretrofit.models.Trap;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * RetrofitService
 */
public interface RetrofitService {

	/* sync */
//	@GET("/traps")
//	Trap getTrap(@Query("id") Integer id);
//
//	@GET("/traps")
//	List<Trap> getTraps();
//
//	/* async */
//	@GET("/traps")
//	void getTrap(@Query("id") Integer id, Callback<Trap> callback);
//
//	@GET("/traps")
//	void getTraps(Callback<List<Trap>> callback);

	/* a-sync */
//	@POST("/")
//	void addTrap(@Body Trap trap, Callback<String> callback);
//	@FormUrlEncoded
//	@POST("/")
//	void addTrap(@Field("trap") Trap trap, Callback<String> callback);

	/* reactive */
	@GET("/")
	Observable<ServerResp<Trap>> getTrap(@Query("id") Integer id);

	@POST("/")
	Observable<ServerResp<Trap>> addTrap(@Body Trap trap);
}
