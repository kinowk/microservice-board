package microservice.board.common.outboxmessagerelay;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AssignedShardTest {

    @Test
    void ofTest() {
        // given
        Long shardCount = 64L;
        List<String> appIds = List.of("appId1", "appId2", "appId3");

        // when
        AssignedShard assignedShard1 = AssignedShard.of(appIds.get(0), appIds, shardCount);
        AssignedShard assignedShard2 = AssignedShard.of(appIds.get(1), appIds, shardCount);
        AssignedShard assignedShard3 = AssignedShard.of(appIds.get(2), appIds, shardCount);
        AssignedShard assignedShard4 = AssignedShard.of("invalid", appIds, shardCount);

        // then
        System.out.println("assignedShard1 = " + assignedShard1);
        System.out.println("assignedShard2 = " + assignedShard2);
        System.out.println("assignedShard3 = " + assignedShard3);
        System.out.println("assignedShard4 = " + assignedShard4);

        List<Long> result = Stream.of(
                        assignedShard1.getShards(),
                        assignedShard2.getShards(),
                        assignedShard3.getShards(),
                        assignedShard4.getShards()
                ).flatMap(List::stream)
                .toList();

        assertThat(result).hasSize(shardCount.intValue());

        for (int i = 0; i < shardCount.intValue(); i++) {
            assertThat(result.get(i)).isEqualTo(i);
        }

        assertThat(assignedShard4.getShards()).isEmpty();
    }
}