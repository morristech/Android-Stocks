package com.example.rest.provider;

import com.example.entity.LookupEntity;
import com.example.entity.QuoteEntity;
import com.example.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class StocksRxServiceProvider
{
	public static final String QUOTE_CALL_TYPE = "quote";
	public static final String LOOKUP_CALL_TYPE = "lookup";

	private static volatile StocksService sService;


	public interface StocksService
	{
		@GET("Quote/{format}")
		Single<Response<QuoteEntity>> quote(@Path("format") String format, @Query("symbol") String symbol);

		@GET("Lookup/{format}")
		Single<Response<List<LookupEntity>>> lookup(@Path("format") String format, @Query("input") String input);
	}


	private StocksRxServiceProvider() {}


	public static StocksService getService()
	{
		if(sService == null)
		{
			synchronized(StocksRxServiceProvider.class)
			{
				if(sService == null)
				{
					sService = RetrofitClient.createService(StocksService.class);
				}
			}
		}
		return sService;
	}
}
