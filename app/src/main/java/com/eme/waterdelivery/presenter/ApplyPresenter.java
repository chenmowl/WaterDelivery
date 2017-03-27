package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.ApplyOneLevelBo;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyPresenter implements ApplyContract.Presenter {

    private static final String TAG=ApplyPresenter.class.getSimpleName();

    private ApplyContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public ApplyPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

    @Override
    public void requestApplyOneLevel() {
        disposables.add(
                retrofitHelper.getApplyOneLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<ApplyOneLevelBo>>() {
                    @Override
                    public void accept(Result<ApplyOneLevelBo> applyOneLevelBoResult) throws Exception {
                        if(applyOneLevelBoResult!=null && applyOneLevelBoResult.isSuccess() && applyOneLevelBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(applyOneLevelBoResult.getData().getCode())){
                            List<ApplyOneLevelBo.ListBean> listBeen=applyOneLevelBoResult.getData().getInfo().getList();
                            if(listBeen!=null && listBeen.size()!=0){
                                view.updateOneLevel(applyOneLevelBoResult.getData().getInfo().getList());
                            }
                        }else{
                            view.requestError();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.requestError();
                    }
                })
        );
    }

    @Override
    public void requestApplyTwoLevel(String id) {
        disposables.add(
                retrofitHelper.getApplyTwoLevel(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Result<ApplyOneLevelBo>>() {
                            @Override
                            public void accept(Result<ApplyOneLevelBo> applyOneLevelBoResult) throws Exception {
                                if(applyOneLevelBoResult!=null && applyOneLevelBoResult.isSuccess() && applyOneLevelBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(applyOneLevelBoResult.getData().getCode())){
                                    List<ApplyOneLevelBo.ListBean> listBeen=applyOneLevelBoResult.getData().getInfo().getList();
                                    if(listBeen!=null && listBeen.size()!=0){
                                        view.updateTwoLevel(applyOneLevelBoResult.getData().getInfo().getList());
                                    }
                                }else{
                                    view.requestError();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.requestError();
                            }
                        })
        );
    }

    @Override
    public void requestApplyTwoLevelGoods(String id) {
        disposables.add(
                retrofitHelper.getApplyTwoLevelGoods(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Result<ApplyTwoLevelGoodBo>>() {
                            @Override
                            public void accept(Result<ApplyTwoLevelGoodBo> applyTwoLevelGoodBoResult) throws Exception {
                                if(applyTwoLevelGoodBoResult!=null && applyTwoLevelGoodBoResult.isSuccess() && applyTwoLevelGoodBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(applyTwoLevelGoodBoResult.getData().getCode())){
                                    List<ApplyTwoLevelGoodBo.ListBean> listBeen=applyTwoLevelGoodBoResult.getData().getInfo().getList();
                                    if(listBeen!=null && listBeen.size()!=0){
                                        view.updateTwoLevelGoods(listBeen);
                                    }
                                }else{
                                    view.requestError();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.requestError();
                            }
                        })
        );
    }

    /**
     * 提交采购单
     * @param storeId
     * @param remark
     * @param json
     */
    @Override
    public void submitApplications(String storeId,String remark,String json) {
        disposables.add(
                retrofitHelper.submitApplications(storeId,remark,json)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StatusResult>() {
                    @Override
                    public void accept(StatusResult statusResult) throws Exception {
                        if(statusResult!=null && statusResult.isSuccess() && statusResult.getData()!=null && Constant.CODE_COMPLETE.equals(statusResult.getData().getCode())){
                            view.showSubmitResult(true,statusResult.getData().getMessage());
                        }else{
                            view.showSubmitResult(false,statusResult.getData().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showSubmitResult(false,null);
                    }
                })
        );
    }
}
