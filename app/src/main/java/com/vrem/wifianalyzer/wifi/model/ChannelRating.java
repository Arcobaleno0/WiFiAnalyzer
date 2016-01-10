/*
 *    Copyright (C) 2015 - 2016 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.vrem.wifianalyzer.wifi.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChannelRating {
    private final static int CHANNEL_OFFSET = 1;

    private Map<Integer, List<DetailsInfo>> wiFiChannels = new TreeMap<>();

    public ChannelRating() {
    }

    public int getCount(int channel) {
        return collectOverlappingChannels(channel).size();
    }

    public Strength getStrength(int channel) {
        Strength strength = Strength.ZERO;
        for (DetailsInfo detailsInfo : collectOverlappingChannels(channel)) {
            if (!detailsInfo.isConnected()) {
                strength = Strength.values()[Math.max(strength.ordinal(), detailsInfo.getStrength().ordinal())];
            }
        }
        return strength;
    }

    public void setWiFiChannels(@NonNull Map<Integer, List<DetailsInfo>> wiFiChannels) {
        this.wiFiChannels = wiFiChannels;
    }

    private List<DetailsInfo> collectOverlappingChannels(int channel) {
        List<DetailsInfo> details = new ArrayList<>();
        for (int i = channel - CHANNEL_OFFSET; i <= channel + CHANNEL_OFFSET; i++) {
            List<DetailsInfo> detailsInfos = wiFiChannels.get(i);
            if (detailsInfos != null) {
                details.addAll(detailsInfos);
            }
        }
        return details;
    }
}
