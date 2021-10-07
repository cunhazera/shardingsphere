/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.metadata.rule;

import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.rule.ShardingSphereRule;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public final class ShardingSphereRuleMetaDataTest {

    private Collection<RuleConfiguration> configurations;

    private Collection<ShardingSphereRule> rules;

    private ShardingSphereRuleMetaData shardingSphereRuleMetaData;

    @Before
    public void init() {
        rules = Arrays.asList(new ShardingSphereRuleFixture());
        shardingSphereRuleMetaData = new ShardingSphereRuleMetaData(configurations, rules);
    }

    @Test
    public void assertFilterRulesReturnOneItem() {
        Collection<ShardingSphereRuleFixture> clazzList = shardingSphereRuleMetaData.findRules(ShardingSphereRuleFixture.class);
        MatcherAssert.assertThat(clazzList, Matchers.hasSize(1));
    }

    @Test
    public void assertFindSingleRuleReturnsEmpty() {
        Optional<MetadataRuleFixture> clazzOptional = shardingSphereRuleMetaData.findSingleRule(MetadataRuleFixture.class);
        MatcherAssert.assertThat(clazzOptional.isPresent(), Matchers.is(false));
    }

    @Test
    public void assertFindSingleRuleHasValue() {
        Optional<ShardingSphereRuleFixture> clazzOptional = shardingSphereRuleMetaData.findSingleRule(ShardingSphereRuleFixture.class);
        MatcherAssert.assertThat(clazzOptional.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(clazzOptional.get().getType(), Matchers.equalTo("ShardingSphereRuleFixture"));
    }
}
