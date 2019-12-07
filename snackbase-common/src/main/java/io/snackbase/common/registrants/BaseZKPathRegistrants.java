package io.snackbase.common.registrants;

import com.google.common.base.Joiner;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * base path : /SnackBase
 * -----------
 * |SnackBase|  ========>replicaDataNodeInfo
 * -----------
 * //     ||       \\
 * //      ||        \\
 * metaNodeInfo   dbInfo   dataNodeInfo
 * <p>
 * {@link BaseZKPathRegistrants#SNACK_BASE_ROOT_PATH}
 *
 * @author maxuefeng
 * @since 2019/12/7
 */
public class BaseZKPathRegistrants extends Registrants {

    private static Logger logger = LoggerFactory.getLogger(BaseZKPathRegistrants.class);
    /**
     * SnackBase root path
     */
    public static final String SNACK_BASE_ROOT_PATH = "/SnackBase";

    /**
     * meta node info
     */
    public static final String META_NODE_BASE_PATH = Joiner.on("/").join(new String[]{
            SNACK_BASE_ROOT_PATH,
            "metaNodeInfo"
    });

    /**
     * DB info node path
     */
    public static final String DB_BASE_PATH = Joiner.on("/").join(new String[]{
            SNACK_BASE_ROOT_PATH,
            "dbInfo"
    });

    /**
     * data node path
     */
    public static final String DATA_NODE_BASE_PATH = Joiner.on("/").join(new String[]{
            SNACK_BASE_ROOT_PATH,
            "dataNode"
    });

    /**
     * replica data node path
     */
    public static final String REPLICA_DATA_NODE_BASE_PATH = Joiner.on("/").join(new String[]{
            SNACK_BASE_ROOT_PATH,
            "replicaDataNode"
    });


    public BaseZKPathRegistrants() throws IOException {
    }

    /**
     * init base path zookeeper node
     */
    public void initializationBasePath() throws Exception {
        registry(SNACK_BASE_ROOT_PATH, SNACK_BASE_ROOT_PATH.getBytes(StandardCharsets.UTF_8), CreateMode.PERSISTENT);
        registry(META_NODE_BASE_PATH, META_NODE_BASE_PATH.getBytes(StandardCharsets.UTF_8), CreateMode.PERSISTENT_SEQUENTIAL);
        registry(DB_BASE_PATH, DB_BASE_PATH.getBytes(StandardCharsets.UTF_8), CreateMode.PERSISTENT_SEQUENTIAL);
        registry(DATA_NODE_BASE_PATH, DATA_NODE_BASE_PATH.getBytes(StandardCharsets.UTF_8), CreateMode.PERSISTENT_SEQUENTIAL);
        registry(REPLICA_DATA_NODE_BASE_PATH, REPLICA_DATA_NODE_BASE_PATH.getBytes(StandardCharsets.UTF_8), CreateMode.PERSISTENT_SEQUENTIAL);
    }

    private void registry(String path, byte[] data, CreateMode createMode) throws KeeperException, InterruptedException {
        // check is exist
        Stat exists = zkClient.exists(path, System.err::println);
        if (exists == null) {
            zkClient.create(path, data, null, createMode);
        }
        logger.info("check base path complete");
    }

}
