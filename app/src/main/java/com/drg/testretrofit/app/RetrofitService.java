package com.drg.testretrofit.app;


import com.drg.testretrofit.models.ObtainTokenModel;
import com.drg.testretrofit.models.Token;
import com.drg.testretrofit.models.base.ServerResp;
import com.drg.testretrofit.models.Trap;

import java.util.List;

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
	Observable<ServerResp<Trap>> getTrap(@Query("token") String token, @Query("id") Integer id);

	@GET("/")
	Observable<ServerResp<List<Trap>>> getTraps(@Query("token") String token);

	@POST("/")
	Observable<ServerResp<Trap>> addTrap(@Body Trap trap);

	@POST("/get_token.php")
	Observable<Token> obtainToken(@Body ObtainTokenModel obtainTokenModel);
}
