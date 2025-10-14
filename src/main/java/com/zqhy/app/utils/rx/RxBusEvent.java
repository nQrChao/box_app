package com.zqhy.app.utils.rx;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Rx事件总线
 * post事件给订阅类发送数据
 * @author 韩国桐
 * @version [0.1, 2017-07-04]
 *          [0.2, 2017-09-27 观察集合采用 CopyOnWriteArrayList]
 * @see Observable
 * @since [事件总线]
 */
public class RxBusEvent {
    private static RxBusEvent instance;

    /**消息更新**/
    public final static String UPDATE_MESSAGE ="UPDATE_MESSAGE";
    /**用户信息更新**/
    public final static String UPDATE_USER ="UPDATE_USER";
    /**更新点数**/
    public final static String UPDATE_POINT ="UPDATE_POINT";
    /**任务**/
    public final static String UPDATE_MISSION ="UPDATE_MISSION";
    /**任务**/
    public final static String UPDATE_TASK ="UPDATE_TASK";

    public static RxBusEvent get() {
        if (instance == null) {
            synchronized (RxBusEvent.class) {
                if (instance == null) {
                    instance = new RxBusEvent();
                }
            }
        }
        return instance;
    }

    private RxBusEvent() {
    }

    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    /**
     * 通过标签判断是否注册监听
     * @param tag 标签
     * @return true(注册) false(未注册)
     * **/
    public boolean isRegister(@NonNull Object tag){
        List<Subject> subjects = subjectMapper.get(tag);
        return null != subjects;
    }

    /**
     * 注册监听
     * @param tag 标签
     * @return 观察监听对象
     * **/
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            //避免注册和发送内容线程不安全，使用CopyOnWriteArrayList进行
            subjectList = new CopyOnWriteArrayList<>();
            subjectMapper.put(tag, subjectList);
        }

        Subject<T> subject= PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }
    /**
     * 取消注册
     * @param tag 标签
     * **/
    public void unregister(@NonNull Object tag) {
        List<Subject> subjects = subjectMapper.get(tag);
        if(isRegister(tag)&&subjects.size()>0){
            subjects.remove(subjects.size()-1);
        }
    }
    /**
     * 发送内容给注册监听
     * @param tag 标签
     * @param content 发送内容
     * **/
    public <T> void post(@NonNull Object tag, T content) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                //noinspection unchecked
                subject.onNext(content);
            }
        }
    }
    /**
     * 对集合是否为空进行判断
     * @param collection 集合
     * **/
    private boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }
}