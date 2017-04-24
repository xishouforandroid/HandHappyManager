package com.liangxunwang.unimanager.baiduyun.channel.client;



import com.liangxunwang.unimanager.baiduyun.channel.exception.ChannelClientException;
import com.liangxunwang.unimanager.baiduyun.channel.exception.ChannelServerException;
import com.liangxunwang.unimanager.baiduyun.channel.model.*;

import java.util.concurrent.Future;

public interface BaiduChannelAsync {

    public Future<PushUnicastMessageResponse> pushUnicastMessageAsync(
            final PushUnicastMessageRequest request)
            throws ChannelClientException, ChannelServerException;

    public Future<Void> pushTagMessageAsync(final PushTagMessageRequest request)
            throws ChannelClientException, ChannelServerException;

    public Future<Void> pushBroadcastMessageAsync(
            final PushBroadcastMessageRequest request)
            throws ChannelClientException, ChannelServerException;

    public Future<QueryBindListResponse> queryBindListAsync(
            final QueryBindListRequest request) throws ChannelClientException,
            ChannelServerException;

    public Future<Void> verifyBindAsync(final VerifyBindRequest request)
            throws ChannelClientException, ChannelServerException;

    public Future<QueryUserTagsResponse> queryUserTagsAsync(
            final QueryUserTagsRequest request) throws ChannelClientException,
            ChannelServerException;

}
