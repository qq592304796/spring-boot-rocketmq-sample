package spring.cloud.bus.rocketmq.common.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import com.google.common.base.MoreObjects;

public class ProductEvent extends RemoteApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    /** 消息类型：更新商品，值为: {@value} */
    public static final String ET_UPDATE = "update";
    /** 消息类型：删除商品，值为: {@value} */
    public static final String ET_DELETE = "delete";

    // ========================================================================
    // fields =================================================================
    private String action;
    private Long bizId;

    // ========================================================================
    // constructor ============================================================
    public ProductEvent() {
        super();
    }

    public ProductEvent(Object source, String originService, String destinationService, String action,
            Long BizId) {
        super(source, originService, destinationService);
        this.action = action;
        this.bizId = bizId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("action", this.getAction()).add("itemCode", this.getId())
                .toString();
    }

    // ==================================================================
    // setter/getter ====================================================
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }
    
}