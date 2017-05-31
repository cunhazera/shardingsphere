/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.routing.type.single;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.context.Column;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.context.condition.Condition;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.expression.SQLExpression;
import com.dangdang.ddframe.rdb.sharding.parsing.parser.expression.SQLNumberExpression;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SingleRouterUtilTest {
    
    @Test
    public void testConvertConditionToShardingValue() throws Exception {
        Condition condition = new Condition(new Column("test", "test"), new SQLNumberExpression(1));
        ShardingValue<?> shardingValue = SingleRouterUtil.convertConditionToShardingValue(condition, Collections.emptyList());
        assertThat(shardingValue.getType(), is(ShardingValue.ShardingValueType.SINGLE));
        assertThat((Integer) shardingValue.getValue(), is(1));
        condition = new Condition(new Column("test", "test"), Arrays.<SQLExpression>asList(new SQLNumberExpression(1), new SQLNumberExpression(2)));
        shardingValue = SingleRouterUtil.convertConditionToShardingValue(condition, Collections.emptyList());
        assertThat(shardingValue.getType(), is(ShardingValue.ShardingValueType.LIST));
        Iterator<?> iterator = shardingValue.getValues().iterator();
        assertThat((Integer) iterator.next(), is(1));
        assertThat((Integer) iterator.next(), is(2));
        condition = new Condition(new Column("test", "test"), new SQLNumberExpression(1), new SQLNumberExpression(2));
        shardingValue = SingleRouterUtil.convertConditionToShardingValue(condition, Collections.emptyList());
        assertThat(shardingValue.getType(), is(ShardingValue.ShardingValueType.RANGE));
        assertThat((Integer) shardingValue.getValueRange().lowerEndpoint(), is(1));
        assertThat((Integer) shardingValue.getValueRange().upperEndpoint(), is(2));
    }
}
