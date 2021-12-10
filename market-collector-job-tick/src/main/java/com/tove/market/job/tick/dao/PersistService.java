package com.tove.market.job.tick.dao;

import com.tove.market.job.tick.model.StockSnapshot;

import java.util.List;

public interface PersistService {
    void saveTick(StockSnapshot stockSnapshot);

    void batchSaveTick(List<StockSnapshot> stockSnapshotList);


}
