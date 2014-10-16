package com.drg.testretrofit.app;

import com.drg.testretrofit.models.base.Egor;
import com.drg.testretrofit.models.base.ServerResp;
import com.google.gson.Gson;

import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * RetrofitApi
 */
public class RetrofitApi {

	private static RetrofitService sInstance;

	private RetrofitApi() {}

	public synchronized static RetrofitService getInstance() {
		if(sInstance == null) {
			sInstance = new RestAdapter.Builder()
					.setEndpoint("http://10.0.3.2/test")
					.setClient(new OkClient()) // avoid bug in @post
					.setConverter(new ProjConverter(new Gson()))
					.build()
					.create(RetrofitService.class);
		}
		return sInstance;
	}


	/**
	 * ProjConverter
	 */
	public static class ProjConverter extends GsonConverter {

		public ProjConverter(Gson gson) {
			super(gson);
		}

		public ProjConverter(Gson gson, String charset) {
			super(gson, charset);
		}

		@Override
		public Object fromBody(TypedInput body, Type type) throws ConversionException {
			Object resp = super.fromBody(body, type);

			if(resp != null && resp instanceof ServerResp) {
				if(((ServerResp)resp).getEgor() != null) {
					throw new ApiException(((ServerResp)resp).getEgor());
				} else if(((ServerResp)resp).getResp() == null) {
					throw new ConversionException("empty response");
				}
			}

			return resp;
		}

		@Override
		public TypedOutput toBody(Object object) {
			return super.toBody(object);
		}
	}

	/**
	 * ApiException
	 */
	public static class ApiException extends ConversionException {

		private Egor mEgor;

		public ApiException(Egor egor) {
			super(egor.getMsg());
			mEgor = egor;
		}

		public Egor getEgor() {
			return mEgor;
		}
	}

}