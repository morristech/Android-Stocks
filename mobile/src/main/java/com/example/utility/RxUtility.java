package com.example.utility;

import com.example.rest.RetrofitHttpException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public final class RxUtility
{
	private RxUtility() {}


	public static <T> Observable.Transformer<T, T> applySchedulers()
	{
		return observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}


	public static <T extends Response<?>> Observable<T> catchHttpError(T response)
	{
		if(RestUtility.isSuccess(response))
		{
			return Observable.just(response);
		}
		else
		{
			return Observable.error(new RetrofitHttpException(response));
		}
	}


	public static String getHttpErrorMessage(Throwable throwable)
	{
		if(throwable instanceof RetrofitHttpException)
		{
			return RestUtility.getErrorMessage((RetrofitHttpException) throwable);
		}
		else
		{
			return RestUtility.getFailMessage(throwable);
		}
	}
}
