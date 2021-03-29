package com.contributor.model.enumeration;

/**
 * User activity status.
 * Used in soft delete, to prevent deleting records
 * from the database. Setting it to INACTIVE or DELETED,
 * will exclude them from showing in fetching data.
 * They will be ACTIVE again, next time they log in.
 * Blocked users can't log-in before the block period.
 */
public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
