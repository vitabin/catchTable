package com.catchtable.api.segment.domain;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SegmentConditions {
    AGE("age", Set.of("gte", "lte", "gt", "le", "eq")),
    LOCATION("location", Set.of("in", "notin")),
    GENDER("gender", Set.of("male", "female")),
    LAST_VISIT_DAY("last_visit_day", Set.of("gte", "lte", "gt", "le", "eq")),
    MONTHLY_VISIT_COUNT("monthly_visit_count", Set.of("gte", "lte", "gt", "le", "eq")),
    PURCHASE_COUNT("purchase_count", Set.of("gte", "lte", "gt", "le", "eq")),
    PURCHASE_AMOUNT("purchase_amount", Set.of("gte", "lte", "gt", "le", "eq")),
    PURCHASE_CATEGORY("purchase_category", Set.of("korean", "italian", "french", "japanese")),
    AD_CLICK_COUNT("ad_click_count", Set.of("gte", "lte", "gt", "le", "eq")),
    EVENT_PARTICIPATION_COUNT("event_participation_count", Set.of("gte", "lte", "gt", "le", "eq")),
    ;

    private static Set<String> TAG_SET;
    private final String name;
    private final Set<String> compares;

    public static Boolean isValid(String tag) {
        return TAG_SET.contains(tag);
    }

    @PostConstruct
    public void init() {
        TAG_SET = Arrays.stream(SegmentConditions.values())
                        .map(Enum::name)
                        .collect(Collectors.toSet());
    }

}
