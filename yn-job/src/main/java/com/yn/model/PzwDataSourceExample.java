package com.yn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PzwDataSourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PzwDataSourceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAmPhaseRecordIdIsNull() {
            addCriterion("am_phase_record_id is null");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdIsNotNull() {
            addCriterion("am_phase_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdEqualTo(String value) {
            addCriterion("am_phase_record_id =", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdNotEqualTo(String value) {
            addCriterion("am_phase_record_id <>", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdGreaterThan(String value) {
            addCriterion("am_phase_record_id >", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("am_phase_record_id >=", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdLessThan(String value) {
            addCriterion("am_phase_record_id <", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdLessThanOrEqualTo(String value) {
            addCriterion("am_phase_record_id <=", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdLike(String value) {
            addCriterion("am_phase_record_id like", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdNotLike(String value) {
            addCriterion("am_phase_record_id not like", value, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdIn(List<String> values) {
            addCriterion("am_phase_record_id in", values, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdNotIn(List<String> values) {
            addCriterion("am_phase_record_id not in", values, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdBetween(String value1, String value2) {
            addCriterion("am_phase_record_id between", value1, value2, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andAmPhaseRecordIdNotBetween(String value1, String value2) {
            addCriterion("am_phase_record_id not between", value1, value2, "amPhaseRecordId");
            return (Criteria) this;
        }

        public Criteria andACurrentIsNull() {
            addCriterion("a_current is null");
            return (Criteria) this;
        }

        public Criteria andACurrentIsNotNull() {
            addCriterion("a_current is not null");
            return (Criteria) this;
        }

        public Criteria andACurrentEqualTo(BigDecimal value) {
            addCriterion("a_current =", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentNotEqualTo(BigDecimal value) {
            addCriterion("a_current <>", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentGreaterThan(BigDecimal value) {
            addCriterion("a_current >", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_current >=", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentLessThan(BigDecimal value) {
            addCriterion("a_current <", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_current <=", value, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentIn(List<BigDecimal> values) {
            addCriterion("a_current in", values, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentNotIn(List<BigDecimal> values) {
            addCriterion("a_current not in", values, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_current between", value1, value2, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andACurrentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_current not between", value1, value2, "aCurrent");
            return (Criteria) this;
        }

        public Criteria andAKvaIsNull() {
            addCriterion("a_kva is null");
            return (Criteria) this;
        }

        public Criteria andAKvaIsNotNull() {
            addCriterion("a_kva is not null");
            return (Criteria) this;
        }

        public Criteria andAKvaEqualTo(BigDecimal value) {
            addCriterion("a_kva =", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaNotEqualTo(BigDecimal value) {
            addCriterion("a_kva <>", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaGreaterThan(BigDecimal value) {
            addCriterion("a_kva >", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kva >=", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaLessThan(BigDecimal value) {
            addCriterion("a_kva <", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kva <=", value, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaIn(List<BigDecimal> values) {
            addCriterion("a_kva in", values, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaNotIn(List<BigDecimal> values) {
            addCriterion("a_kva not in", values, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kva between", value1, value2, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kva not between", value1, value2, "aKva");
            return (Criteria) this;
        }

        public Criteria andAKvarIsNull() {
            addCriterion("a_kvar is null");
            return (Criteria) this;
        }

        public Criteria andAKvarIsNotNull() {
            addCriterion("a_kvar is not null");
            return (Criteria) this;
        }

        public Criteria andAKvarEqualTo(BigDecimal value) {
            addCriterion("a_kvar =", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarNotEqualTo(BigDecimal value) {
            addCriterion("a_kvar <>", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarGreaterThan(BigDecimal value) {
            addCriterion("a_kvar >", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kvar >=", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarLessThan(BigDecimal value) {
            addCriterion("a_kvar <", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kvar <=", value, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarIn(List<BigDecimal> values) {
            addCriterion("a_kvar in", values, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarNotIn(List<BigDecimal> values) {
            addCriterion("a_kvar not in", values, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kvar between", value1, value2, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKvarNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kvar not between", value1, value2, "aKvar");
            return (Criteria) this;
        }

        public Criteria andAKwIsNull() {
            addCriterion("a_kw is null");
            return (Criteria) this;
        }

        public Criteria andAKwIsNotNull() {
            addCriterion("a_kw is not null");
            return (Criteria) this;
        }

        public Criteria andAKwEqualTo(BigDecimal value) {
            addCriterion("a_kw =", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwNotEqualTo(BigDecimal value) {
            addCriterion("a_kw <>", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwGreaterThan(BigDecimal value) {
            addCriterion("a_kw >", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kw >=", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwLessThan(BigDecimal value) {
            addCriterion("a_kw <", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kw <=", value, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwIn(List<BigDecimal> values) {
            addCriterion("a_kw in", values, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwNotIn(List<BigDecimal> values) {
            addCriterion("a_kw not in", values, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kw between", value1, value2, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kw not between", value1, value2, "aKw");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalIsNull() {
            addCriterion("a_kwh_total is null");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalIsNotNull() {
            addCriterion("a_kwh_total is not null");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalEqualTo(BigDecimal value) {
            addCriterion("a_kwh_total =", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalNotEqualTo(BigDecimal value) {
            addCriterion("a_kwh_total <>", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalGreaterThan(BigDecimal value) {
            addCriterion("a_kwh_total >", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kwh_total >=", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalLessThan(BigDecimal value) {
            addCriterion("a_kwh_total <", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_kwh_total <=", value, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalIn(List<BigDecimal> values) {
            addCriterion("a_kwh_total in", values, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalNotIn(List<BigDecimal> values) {
            addCriterion("a_kwh_total not in", values, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kwh_total between", value1, value2, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAKwhTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_kwh_total not between", value1, value2, "aKwhTotal");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorIsNull() {
            addCriterion("a_power_factor is null");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorIsNotNull() {
            addCriterion("a_power_factor is not null");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorEqualTo(BigDecimal value) {
            addCriterion("a_power_factor =", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorNotEqualTo(BigDecimal value) {
            addCriterion("a_power_factor <>", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorGreaterThan(BigDecimal value) {
            addCriterion("a_power_factor >", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_power_factor >=", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorLessThan(BigDecimal value) {
            addCriterion("a_power_factor <", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_power_factor <=", value, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorIn(List<BigDecimal> values) {
            addCriterion("a_power_factor in", values, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorNotIn(List<BigDecimal> values) {
            addCriterion("a_power_factor not in", values, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_power_factor between", value1, value2, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAPowerFactorNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_power_factor not between", value1, value2, "aPowerFactor");
            return (Criteria) this;
        }

        public Criteria andAVoltIsNull() {
            addCriterion("a_volt is null");
            return (Criteria) this;
        }

        public Criteria andAVoltIsNotNull() {
            addCriterion("a_volt is not null");
            return (Criteria) this;
        }

        public Criteria andAVoltEqualTo(BigDecimal value) {
            addCriterion("a_volt =", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltNotEqualTo(BigDecimal value) {
            addCriterion("a_volt <>", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltGreaterThan(BigDecimal value) {
            addCriterion("a_volt >", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("a_volt >=", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltLessThan(BigDecimal value) {
            addCriterion("a_volt <", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("a_volt <=", value, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltIn(List<BigDecimal> values) {
            addCriterion("a_volt in", values, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltNotIn(List<BigDecimal> values) {
            addCriterion("a_volt not in", values, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_volt between", value1, value2, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("a_volt not between", value1, value2, "aVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltIsNull() {
            addCriterion("ab_volt is null");
            return (Criteria) this;
        }

        public Criteria andAbVoltIsNotNull() {
            addCriterion("ab_volt is not null");
            return (Criteria) this;
        }

        public Criteria andAbVoltEqualTo(BigDecimal value) {
            addCriterion("ab_volt =", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltNotEqualTo(BigDecimal value) {
            addCriterion("ab_volt <>", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltGreaterThan(BigDecimal value) {
            addCriterion("ab_volt >", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ab_volt >=", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltLessThan(BigDecimal value) {
            addCriterion("ab_volt <", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ab_volt <=", value, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltIn(List<BigDecimal> values) {
            addCriterion("ab_volt in", values, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltNotIn(List<BigDecimal> values) {
            addCriterion("ab_volt not in", values, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ab_volt between", value1, value2, "abVolt");
            return (Criteria) this;
        }

        public Criteria andAbVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ab_volt not between", value1, value2, "abVolt");
            return (Criteria) this;
        }

        public Criteria andBCurrentIsNull() {
            addCriterion("b_current is null");
            return (Criteria) this;
        }

        public Criteria andBCurrentIsNotNull() {
            addCriterion("b_current is not null");
            return (Criteria) this;
        }

        public Criteria andBCurrentEqualTo(BigDecimal value) {
            addCriterion("b_current =", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentNotEqualTo(BigDecimal value) {
            addCriterion("b_current <>", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentGreaterThan(BigDecimal value) {
            addCriterion("b_current >", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_current >=", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentLessThan(BigDecimal value) {
            addCriterion("b_current <", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_current <=", value, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentIn(List<BigDecimal> values) {
            addCriterion("b_current in", values, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentNotIn(List<BigDecimal> values) {
            addCriterion("b_current not in", values, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_current between", value1, value2, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBCurrentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_current not between", value1, value2, "bCurrent");
            return (Criteria) this;
        }

        public Criteria andBKvaIsNull() {
            addCriterion("b_kva is null");
            return (Criteria) this;
        }

        public Criteria andBKvaIsNotNull() {
            addCriterion("b_kva is not null");
            return (Criteria) this;
        }

        public Criteria andBKvaEqualTo(BigDecimal value) {
            addCriterion("b_kva =", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaNotEqualTo(BigDecimal value) {
            addCriterion("b_kva <>", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaGreaterThan(BigDecimal value) {
            addCriterion("b_kva >", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kva >=", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaLessThan(BigDecimal value) {
            addCriterion("b_kva <", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kva <=", value, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaIn(List<BigDecimal> values) {
            addCriterion("b_kva in", values, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaNotIn(List<BigDecimal> values) {
            addCriterion("b_kva not in", values, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kva between", value1, value2, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kva not between", value1, value2, "bKva");
            return (Criteria) this;
        }

        public Criteria andBKvarIsNull() {
            addCriterion("b_kvar is null");
            return (Criteria) this;
        }

        public Criteria andBKvarIsNotNull() {
            addCriterion("b_kvar is not null");
            return (Criteria) this;
        }

        public Criteria andBKvarEqualTo(BigDecimal value) {
            addCriterion("b_kvar =", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarNotEqualTo(BigDecimal value) {
            addCriterion("b_kvar <>", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarGreaterThan(BigDecimal value) {
            addCriterion("b_kvar >", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kvar >=", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarLessThan(BigDecimal value) {
            addCriterion("b_kvar <", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kvar <=", value, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarIn(List<BigDecimal> values) {
            addCriterion("b_kvar in", values, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarNotIn(List<BigDecimal> values) {
            addCriterion("b_kvar not in", values, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kvar between", value1, value2, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKvarNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kvar not between", value1, value2, "bKvar");
            return (Criteria) this;
        }

        public Criteria andBKwIsNull() {
            addCriterion("b_kw is null");
            return (Criteria) this;
        }

        public Criteria andBKwIsNotNull() {
            addCriterion("b_kw is not null");
            return (Criteria) this;
        }

        public Criteria andBKwEqualTo(BigDecimal value) {
            addCriterion("b_kw =", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwNotEqualTo(BigDecimal value) {
            addCriterion("b_kw <>", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwGreaterThan(BigDecimal value) {
            addCriterion("b_kw >", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kw >=", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwLessThan(BigDecimal value) {
            addCriterion("b_kw <", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kw <=", value, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwIn(List<BigDecimal> values) {
            addCriterion("b_kw in", values, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwNotIn(List<BigDecimal> values) {
            addCriterion("b_kw not in", values, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kw between", value1, value2, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kw not between", value1, value2, "bKw");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalIsNull() {
            addCriterion("b_kwh_total is null");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalIsNotNull() {
            addCriterion("b_kwh_total is not null");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalEqualTo(BigDecimal value) {
            addCriterion("b_kwh_total =", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalNotEqualTo(BigDecimal value) {
            addCriterion("b_kwh_total <>", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalGreaterThan(BigDecimal value) {
            addCriterion("b_kwh_total >", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kwh_total >=", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalLessThan(BigDecimal value) {
            addCriterion("b_kwh_total <", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_kwh_total <=", value, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalIn(List<BigDecimal> values) {
            addCriterion("b_kwh_total in", values, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalNotIn(List<BigDecimal> values) {
            addCriterion("b_kwh_total not in", values, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kwh_total between", value1, value2, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBKwhTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_kwh_total not between", value1, value2, "bKwhTotal");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorIsNull() {
            addCriterion("b_power_factor is null");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorIsNotNull() {
            addCriterion("b_power_factor is not null");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorEqualTo(BigDecimal value) {
            addCriterion("b_power_factor =", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorNotEqualTo(BigDecimal value) {
            addCriterion("b_power_factor <>", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorGreaterThan(BigDecimal value) {
            addCriterion("b_power_factor >", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_power_factor >=", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorLessThan(BigDecimal value) {
            addCriterion("b_power_factor <", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_power_factor <=", value, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorIn(List<BigDecimal> values) {
            addCriterion("b_power_factor in", values, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorNotIn(List<BigDecimal> values) {
            addCriterion("b_power_factor not in", values, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_power_factor between", value1, value2, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBPowerFactorNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_power_factor not between", value1, value2, "bPowerFactor");
            return (Criteria) this;
        }

        public Criteria andBVoltIsNull() {
            addCriterion("b_volt is null");
            return (Criteria) this;
        }

        public Criteria andBVoltIsNotNull() {
            addCriterion("b_volt is not null");
            return (Criteria) this;
        }

        public Criteria andBVoltEqualTo(BigDecimal value) {
            addCriterion("b_volt =", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltNotEqualTo(BigDecimal value) {
            addCriterion("b_volt <>", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltGreaterThan(BigDecimal value) {
            addCriterion("b_volt >", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("b_volt >=", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltLessThan(BigDecimal value) {
            addCriterion("b_volt <", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("b_volt <=", value, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltIn(List<BigDecimal> values) {
            addCriterion("b_volt in", values, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltNotIn(List<BigDecimal> values) {
            addCriterion("b_volt not in", values, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_volt between", value1, value2, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("b_volt not between", value1, value2, "bVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltIsNull() {
            addCriterion("bc_volt is null");
            return (Criteria) this;
        }

        public Criteria andBcVoltIsNotNull() {
            addCriterion("bc_volt is not null");
            return (Criteria) this;
        }

        public Criteria andBcVoltEqualTo(BigDecimal value) {
            addCriterion("bc_volt =", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltNotEqualTo(BigDecimal value) {
            addCriterion("bc_volt <>", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltGreaterThan(BigDecimal value) {
            addCriterion("bc_volt >", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bc_volt >=", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltLessThan(BigDecimal value) {
            addCriterion("bc_volt <", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bc_volt <=", value, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltIn(List<BigDecimal> values) {
            addCriterion("bc_volt in", values, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltNotIn(List<BigDecimal> values) {
            addCriterion("bc_volt not in", values, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bc_volt between", value1, value2, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andBcVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bc_volt not between", value1, value2, "bcVolt");
            return (Criteria) this;
        }

        public Criteria andCAddrIsNull() {
            addCriterion("c_addr is null");
            return (Criteria) this;
        }

        public Criteria andCAddrIsNotNull() {
            addCriterion("c_addr is not null");
            return (Criteria) this;
        }

        public Criteria andCAddrEqualTo(Integer value) {
            addCriterion("c_addr =", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrNotEqualTo(Integer value) {
            addCriterion("c_addr <>", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrGreaterThan(Integer value) {
            addCriterion("c_addr >", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrGreaterThanOrEqualTo(Integer value) {
            addCriterion("c_addr >=", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrLessThan(Integer value) {
            addCriterion("c_addr <", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrLessThanOrEqualTo(Integer value) {
            addCriterion("c_addr <=", value, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrIn(List<Integer> values) {
            addCriterion("c_addr in", values, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrNotIn(List<Integer> values) {
            addCriterion("c_addr not in", values, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrBetween(Integer value1, Integer value2) {
            addCriterion("c_addr between", value1, value2, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCAddrNotBetween(Integer value1, Integer value2) {
            addCriterion("c_addr not between", value1, value2, "cAddr");
            return (Criteria) this;
        }

        public Criteria andCCurrentIsNull() {
            addCriterion("c_current is null");
            return (Criteria) this;
        }

        public Criteria andCCurrentIsNotNull() {
            addCriterion("c_current is not null");
            return (Criteria) this;
        }

        public Criteria andCCurrentEqualTo(BigDecimal value) {
            addCriterion("c_current =", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentNotEqualTo(BigDecimal value) {
            addCriterion("c_current <>", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentGreaterThan(BigDecimal value) {
            addCriterion("c_current >", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_current >=", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentLessThan(BigDecimal value) {
            addCriterion("c_current <", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_current <=", value, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentIn(List<BigDecimal> values) {
            addCriterion("c_current in", values, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentNotIn(List<BigDecimal> values) {
            addCriterion("c_current not in", values, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_current between", value1, value2, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCCurrentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_current not between", value1, value2, "cCurrent");
            return (Criteria) this;
        }

        public Criteria andCKvaIsNull() {
            addCriterion("c_kva is null");
            return (Criteria) this;
        }

        public Criteria andCKvaIsNotNull() {
            addCriterion("c_kva is not null");
            return (Criteria) this;
        }

        public Criteria andCKvaEqualTo(BigDecimal value) {
            addCriterion("c_kva =", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaNotEqualTo(BigDecimal value) {
            addCriterion("c_kva <>", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaGreaterThan(BigDecimal value) {
            addCriterion("c_kva >", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kva >=", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaLessThan(BigDecimal value) {
            addCriterion("c_kva <", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kva <=", value, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaIn(List<BigDecimal> values) {
            addCriterion("c_kva in", values, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaNotIn(List<BigDecimal> values) {
            addCriterion("c_kva not in", values, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kva between", value1, value2, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kva not between", value1, value2, "cKva");
            return (Criteria) this;
        }

        public Criteria andCKvarIsNull() {
            addCriterion("c_kvar is null");
            return (Criteria) this;
        }

        public Criteria andCKvarIsNotNull() {
            addCriterion("c_kvar is not null");
            return (Criteria) this;
        }

        public Criteria andCKvarEqualTo(BigDecimal value) {
            addCriterion("c_kvar =", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarNotEqualTo(BigDecimal value) {
            addCriterion("c_kvar <>", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarGreaterThan(BigDecimal value) {
            addCriterion("c_kvar >", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kvar >=", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarLessThan(BigDecimal value) {
            addCriterion("c_kvar <", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kvar <=", value, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarIn(List<BigDecimal> values) {
            addCriterion("c_kvar in", values, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarNotIn(List<BigDecimal> values) {
            addCriterion("c_kvar not in", values, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kvar between", value1, value2, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKvarNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kvar not between", value1, value2, "cKvar");
            return (Criteria) this;
        }

        public Criteria andCKwIsNull() {
            addCriterion("c_kw is null");
            return (Criteria) this;
        }

        public Criteria andCKwIsNotNull() {
            addCriterion("c_kw is not null");
            return (Criteria) this;
        }

        public Criteria andCKwEqualTo(BigDecimal value) {
            addCriterion("c_kw =", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwNotEqualTo(BigDecimal value) {
            addCriterion("c_kw <>", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwGreaterThan(BigDecimal value) {
            addCriterion("c_kw >", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kw >=", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwLessThan(BigDecimal value) {
            addCriterion("c_kw <", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kw <=", value, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwIn(List<BigDecimal> values) {
            addCriterion("c_kw in", values, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwNotIn(List<BigDecimal> values) {
            addCriterion("c_kw not in", values, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kw between", value1, value2, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kw not between", value1, value2, "cKw");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalIsNull() {
            addCriterion("c_kwh_total is null");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalIsNotNull() {
            addCriterion("c_kwh_total is not null");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalEqualTo(BigDecimal value) {
            addCriterion("c_kwh_total =", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalNotEqualTo(BigDecimal value) {
            addCriterion("c_kwh_total <>", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalGreaterThan(BigDecimal value) {
            addCriterion("c_kwh_total >", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kwh_total >=", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalLessThan(BigDecimal value) {
            addCriterion("c_kwh_total <", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_kwh_total <=", value, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalIn(List<BigDecimal> values) {
            addCriterion("c_kwh_total in", values, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalNotIn(List<BigDecimal> values) {
            addCriterion("c_kwh_total not in", values, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kwh_total between", value1, value2, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCKwhTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_kwh_total not between", value1, value2, "cKwhTotal");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorIsNull() {
            addCriterion("c_power_factor is null");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorIsNotNull() {
            addCriterion("c_power_factor is not null");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorEqualTo(BigDecimal value) {
            addCriterion("c_power_factor =", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorNotEqualTo(BigDecimal value) {
            addCriterion("c_power_factor <>", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorGreaterThan(BigDecimal value) {
            addCriterion("c_power_factor >", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_power_factor >=", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorLessThan(BigDecimal value) {
            addCriterion("c_power_factor <", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_power_factor <=", value, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorIn(List<BigDecimal> values) {
            addCriterion("c_power_factor in", values, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorNotIn(List<BigDecimal> values) {
            addCriterion("c_power_factor not in", values, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_power_factor between", value1, value2, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCPowerFactorNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_power_factor not between", value1, value2, "cPowerFactor");
            return (Criteria) this;
        }

        public Criteria andCVoltIsNull() {
            addCriterion("c_volt is null");
            return (Criteria) this;
        }

        public Criteria andCVoltIsNotNull() {
            addCriterion("c_volt is not null");
            return (Criteria) this;
        }

        public Criteria andCVoltEqualTo(BigDecimal value) {
            addCriterion("c_volt =", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltNotEqualTo(BigDecimal value) {
            addCriterion("c_volt <>", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltGreaterThan(BigDecimal value) {
            addCriterion("c_volt >", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("c_volt >=", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltLessThan(BigDecimal value) {
            addCriterion("c_volt <", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("c_volt <=", value, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltIn(List<BigDecimal> values) {
            addCriterion("c_volt in", values, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltNotIn(List<BigDecimal> values) {
            addCriterion("c_volt not in", values, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_volt between", value1, value2, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("c_volt not between", value1, value2, "cVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltIsNull() {
            addCriterion("ca_volt is null");
            return (Criteria) this;
        }

        public Criteria andCaVoltIsNotNull() {
            addCriterion("ca_volt is not null");
            return (Criteria) this;
        }

        public Criteria andCaVoltEqualTo(BigDecimal value) {
            addCriterion("ca_volt =", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltNotEqualTo(BigDecimal value) {
            addCriterion("ca_volt <>", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltGreaterThan(BigDecimal value) {
            addCriterion("ca_volt >", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ca_volt >=", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltLessThan(BigDecimal value) {
            addCriterion("ca_volt <", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ca_volt <=", value, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltIn(List<BigDecimal> values) {
            addCriterion("ca_volt in", values, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltNotIn(List<BigDecimal> values) {
            addCriterion("ca_volt not in", values, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ca_volt between", value1, value2, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCaVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ca_volt not between", value1, value2, "caVolt");
            return (Criteria) this;
        }

        public Criteria andCurrentIsNull() {
            addCriterion("current is null");
            return (Criteria) this;
        }

        public Criteria andCurrentIsNotNull() {
            addCriterion("current is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentEqualTo(BigDecimal value) {
            addCriterion("current =", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotEqualTo(BigDecimal value) {
            addCriterion("current <>", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentGreaterThan(BigDecimal value) {
            addCriterion("current >", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("current >=", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentLessThan(BigDecimal value) {
            addCriterion("current <", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("current <=", value, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentIn(List<BigDecimal> values) {
            addCriterion("current in", values, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotIn(List<BigDecimal> values) {
            addCriterion("current not in", values, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current between", value1, value2, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current not between", value1, value2, "current");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeIsNull() {
            addCriterion("current_change is null");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeIsNotNull() {
            addCriterion("current_change is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeEqualTo(Integer value) {
            addCriterion("current_change =", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeNotEqualTo(Integer value) {
            addCriterion("current_change <>", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeGreaterThan(Integer value) {
            addCriterion("current_change >", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_change >=", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeLessThan(Integer value) {
            addCriterion("current_change <", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeLessThanOrEqualTo(Integer value) {
            addCriterion("current_change <=", value, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeIn(List<Integer> values) {
            addCriterion("current_change in", values, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeNotIn(List<Integer> values) {
            addCriterion("current_change not in", values, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeBetween(Integer value1, Integer value2) {
            addCriterion("current_change between", value1, value2, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentChangeNotBetween(Integer value1, Integer value2) {
            addCriterion("current_change not between", value1, value2, "currentChange");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroIsNull() {
            addCriterion("current_zero is null");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroIsNotNull() {
            addCriterion("current_zero is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroEqualTo(BigDecimal value) {
            addCriterion("current_zero =", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroNotEqualTo(BigDecimal value) {
            addCriterion("current_zero <>", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroGreaterThan(BigDecimal value) {
            addCriterion("current_zero >", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("current_zero >=", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroLessThan(BigDecimal value) {
            addCriterion("current_zero <", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroLessThanOrEqualTo(BigDecimal value) {
            addCriterion("current_zero <=", value, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroIn(List<BigDecimal> values) {
            addCriterion("current_zero in", values, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroNotIn(List<BigDecimal> values) {
            addCriterion("current_zero not in", values, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_zero between", value1, value2, "currentZero");
            return (Criteria) this;
        }

        public Criteria andCurrentZeroNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_zero not between", value1, value2, "currentZero");
            return (Criteria) this;
        }

        public Criteria andDAddrIsNull() {
            addCriterion("d_addr is null");
            return (Criteria) this;
        }

        public Criteria andDAddrIsNotNull() {
            addCriterion("d_addr is not null");
            return (Criteria) this;
        }

        public Criteria andDAddrEqualTo(Long value) {
            addCriterion("d_addr =", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotEqualTo(Long value) {
            addCriterion("d_addr <>", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrGreaterThan(Long value) {
            addCriterion("d_addr >", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrGreaterThanOrEqualTo(Long value) {
            addCriterion("d_addr >=", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrLessThan(Long value) {
            addCriterion("d_addr <", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrLessThanOrEqualTo(Long value) {
            addCriterion("d_addr <=", value, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrIn(List<Long> values) {
            addCriterion("d_addr in", values, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotIn(List<Long> values) {
            addCriterion("d_addr not in", values, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrBetween(Long value1, Long value2) {
            addCriterion("d_addr between", value1, value2, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDAddrNotBetween(Long value1, Long value2) {
            addCriterion("d_addr not between", value1, value2, "dAddr");
            return (Criteria) this;
        }

        public Criteria andDTypeIsNull() {
            addCriterion("d_type is null");
            return (Criteria) this;
        }

        public Criteria andDTypeIsNotNull() {
            addCriterion("d_type is not null");
            return (Criteria) this;
        }

        public Criteria andDTypeEqualTo(Integer value) {
            addCriterion("d_type =", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotEqualTo(Integer value) {
            addCriterion("d_type <>", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeGreaterThan(Integer value) {
            addCriterion("d_type >", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("d_type >=", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeLessThan(Integer value) {
            addCriterion("d_type <", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeLessThanOrEqualTo(Integer value) {
            addCriterion("d_type <=", value, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeIn(List<Integer> values) {
            addCriterion("d_type in", values, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotIn(List<Integer> values) {
            addCriterion("d_type not in", values, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeBetween(Integer value1, Integer value2) {
            addCriterion("d_type between", value1, value2, "dType");
            return (Criteria) this;
        }

        public Criteria andDTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("d_type not between", value1, value2, "dType");
            return (Criteria) this;
        }

        public Criteria andDealtIsNull() {
            addCriterion("dealt is null");
            return (Criteria) this;
        }

        public Criteria andDealtIsNotNull() {
            addCriterion("dealt is not null");
            return (Criteria) this;
        }

        public Criteria andDealtEqualTo(Integer value) {
            addCriterion("dealt =", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtNotEqualTo(Integer value) {
            addCriterion("dealt <>", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtGreaterThan(Integer value) {
            addCriterion("dealt >", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtGreaterThanOrEqualTo(Integer value) {
            addCriterion("dealt >=", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtLessThan(Integer value) {
            addCriterion("dealt <", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtLessThanOrEqualTo(Integer value) {
            addCriterion("dealt <=", value, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtIn(List<Integer> values) {
            addCriterion("dealt in", values, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtNotIn(List<Integer> values) {
            addCriterion("dealt not in", values, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtBetween(Integer value1, Integer value2) {
            addCriterion("dealt between", value1, value2, "dealt");
            return (Criteria) this;
        }

        public Criteria andDealtNotBetween(Integer value1, Integer value2) {
            addCriterion("dealt not between", value1, value2, "dealt");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNull() {
            addCriterion("frequency is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNotNull() {
            addCriterion("frequency is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyEqualTo(BigDecimal value) {
            addCriterion("frequency =", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotEqualTo(BigDecimal value) {
            addCriterion("frequency <>", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThan(BigDecimal value) {
            addCriterion("frequency >", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frequency >=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThan(BigDecimal value) {
            addCriterion("frequency <", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frequency <=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyIn(List<BigDecimal> values) {
            addCriterion("frequency in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotIn(List<BigDecimal> values) {
            addCriterion("frequency not in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frequency between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frequency not between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andIAddrIsNull() {
            addCriterion("i_addr is null");
            return (Criteria) this;
        }

        public Criteria andIAddrIsNotNull() {
            addCriterion("i_addr is not null");
            return (Criteria) this;
        }

        public Criteria andIAddrEqualTo(Integer value) {
            addCriterion("i_addr =", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrNotEqualTo(Integer value) {
            addCriterion("i_addr <>", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrGreaterThan(Integer value) {
            addCriterion("i_addr >", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrGreaterThanOrEqualTo(Integer value) {
            addCriterion("i_addr >=", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrLessThan(Integer value) {
            addCriterion("i_addr <", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrLessThanOrEqualTo(Integer value) {
            addCriterion("i_addr <=", value, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrIn(List<Integer> values) {
            addCriterion("i_addr in", values, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrNotIn(List<Integer> values) {
            addCriterion("i_addr not in", values, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrBetween(Integer value1, Integer value2) {
            addCriterion("i_addr between", value1, value2, "iAddr");
            return (Criteria) this;
        }

        public Criteria andIAddrNotBetween(Integer value1, Integer value2) {
            addCriterion("i_addr not between", value1, value2, "iAddr");
            return (Criteria) this;
        }

        public Criteria andKvaIsNull() {
            addCriterion("kva is null");
            return (Criteria) this;
        }

        public Criteria andKvaIsNotNull() {
            addCriterion("kva is not null");
            return (Criteria) this;
        }

        public Criteria andKvaEqualTo(BigDecimal value) {
            addCriterion("kva =", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaNotEqualTo(BigDecimal value) {
            addCriterion("kva <>", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaGreaterThan(BigDecimal value) {
            addCriterion("kva >", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kva >=", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaLessThan(BigDecimal value) {
            addCriterion("kva <", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kva <=", value, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaIn(List<BigDecimal> values) {
            addCriterion("kva in", values, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaNotIn(List<BigDecimal> values) {
            addCriterion("kva not in", values, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kva between", value1, value2, "kva");
            return (Criteria) this;
        }

        public Criteria andKvaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kva not between", value1, value2, "kva");
            return (Criteria) this;
        }

        public Criteria andKvarIsNull() {
            addCriterion("kvar is null");
            return (Criteria) this;
        }

        public Criteria andKvarIsNotNull() {
            addCriterion("kvar is not null");
            return (Criteria) this;
        }

        public Criteria andKvarEqualTo(BigDecimal value) {
            addCriterion("kvar =", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarNotEqualTo(BigDecimal value) {
            addCriterion("kvar <>", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarGreaterThan(BigDecimal value) {
            addCriterion("kvar >", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kvar >=", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarLessThan(BigDecimal value) {
            addCriterion("kvar <", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kvar <=", value, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarIn(List<BigDecimal> values) {
            addCriterion("kvar in", values, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarNotIn(List<BigDecimal> values) {
            addCriterion("kvar not in", values, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvar between", value1, value2, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvar not between", value1, value2, "kvar");
            return (Criteria) this;
        }

        public Criteria andKvarh1IsNull() {
            addCriterion("kvarh1 is null");
            return (Criteria) this;
        }

        public Criteria andKvarh1IsNotNull() {
            addCriterion("kvarh1 is not null");
            return (Criteria) this;
        }

        public Criteria andKvarh1EqualTo(BigDecimal value) {
            addCriterion("kvarh1 =", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1NotEqualTo(BigDecimal value) {
            addCriterion("kvarh1 <>", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1GreaterThan(BigDecimal value) {
            addCriterion("kvarh1 >", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kvarh1 >=", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1LessThan(BigDecimal value) {
            addCriterion("kvarh1 <", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("kvarh1 <=", value, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1In(List<BigDecimal> values) {
            addCriterion("kvarh1 in", values, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1NotIn(List<BigDecimal> values) {
            addCriterion("kvarh1 not in", values, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvarh1 between", value1, value2, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvarh1 not between", value1, value2, "kvarh1");
            return (Criteria) this;
        }

        public Criteria andKvarh2IsNull() {
            addCriterion("kvarh2 is null");
            return (Criteria) this;
        }

        public Criteria andKvarh2IsNotNull() {
            addCriterion("kvarh2 is not null");
            return (Criteria) this;
        }

        public Criteria andKvarh2EqualTo(BigDecimal value) {
            addCriterion("kvarh2 =", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2NotEqualTo(BigDecimal value) {
            addCriterion("kvarh2 <>", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2GreaterThan(BigDecimal value) {
            addCriterion("kvarh2 >", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kvarh2 >=", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2LessThan(BigDecimal value) {
            addCriterion("kvarh2 <", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2LessThanOrEqualTo(BigDecimal value) {
            addCriterion("kvarh2 <=", value, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2In(List<BigDecimal> values) {
            addCriterion("kvarh2 in", values, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2NotIn(List<BigDecimal> values) {
            addCriterion("kvarh2 not in", values, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvarh2 between", value1, value2, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKvarh2NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kvarh2 not between", value1, value2, "kvarh2");
            return (Criteria) this;
        }

        public Criteria andKwIsNull() {
            addCriterion("kw is null");
            return (Criteria) this;
        }

        public Criteria andKwIsNotNull() {
            addCriterion("kw is not null");
            return (Criteria) this;
        }

        public Criteria andKwEqualTo(BigDecimal value) {
            addCriterion("kw =", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotEqualTo(BigDecimal value) {
            addCriterion("kw <>", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwGreaterThan(BigDecimal value) {
            addCriterion("kw >", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kw >=", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwLessThan(BigDecimal value) {
            addCriterion("kw <", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kw <=", value, "kw");
            return (Criteria) this;
        }

        public Criteria andKwIn(List<BigDecimal> values) {
            addCriterion("kw in", values, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotIn(List<BigDecimal> values) {
            addCriterion("kw not in", values, "kw");
            return (Criteria) this;
        }

        public Criteria andKwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kw between", value1, value2, "kw");
            return (Criteria) this;
        }

        public Criteria andKwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kw not between", value1, value2, "kw");
            return (Criteria) this;
        }

        public Criteria andKwhIsNull() {
            addCriterion("kwh is null");
            return (Criteria) this;
        }

        public Criteria andKwhIsNotNull() {
            addCriterion("kwh is not null");
            return (Criteria) this;
        }

        public Criteria andKwhEqualTo(BigDecimal value) {
            addCriterion("kwh =", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotEqualTo(BigDecimal value) {
            addCriterion("kwh <>", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhGreaterThan(BigDecimal value) {
            addCriterion("kwh >", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh >=", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhLessThan(BigDecimal value) {
            addCriterion("kwh <", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh <=", value, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhIn(List<BigDecimal> values) {
            addCriterion("kwh in", values, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotIn(List<BigDecimal> values) {
            addCriterion("kwh not in", values, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh between", value1, value2, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh not between", value1, value2, "kwh");
            return (Criteria) this;
        }

        public Criteria andKwhRevIsNull() {
            addCriterion("kwh_rev is null");
            return (Criteria) this;
        }

        public Criteria andKwhRevIsNotNull() {
            addCriterion("kwh_rev is not null");
            return (Criteria) this;
        }

        public Criteria andKwhRevEqualTo(BigDecimal value) {
            addCriterion("kwh_rev =", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevNotEqualTo(BigDecimal value) {
            addCriterion("kwh_rev <>", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevGreaterThan(BigDecimal value) {
            addCriterion("kwh_rev >", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_rev >=", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevLessThan(BigDecimal value) {
            addCriterion("kwh_rev <", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_rev <=", value, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevIn(List<BigDecimal> values) {
            addCriterion("kwh_rev in", values, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevNotIn(List<BigDecimal> values) {
            addCriterion("kwh_rev not in", values, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_rev between", value1, value2, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhRevNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_rev not between", value1, value2, "kwhRev");
            return (Criteria) this;
        }

        public Criteria andKwhTotalIsNull() {
            addCriterion("kwh_total is null");
            return (Criteria) this;
        }

        public Criteria andKwhTotalIsNotNull() {
            addCriterion("kwh_total is not null");
            return (Criteria) this;
        }

        public Criteria andKwhTotalEqualTo(BigDecimal value) {
            addCriterion("kwh_total =", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotEqualTo(BigDecimal value) {
            addCriterion("kwh_total <>", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalGreaterThan(BigDecimal value) {
            addCriterion("kwh_total >", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_total >=", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalLessThan(BigDecimal value) {
            addCriterion("kwh_total <", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("kwh_total <=", value, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalIn(List<BigDecimal> values) {
            addCriterion("kwh_total in", values, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotIn(List<BigDecimal> values) {
            addCriterion("kwh_total not in", values, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_total between", value1, value2, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andKwhTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("kwh_total not between", value1, value2, "kwhTotal");
            return (Criteria) this;
        }

        public Criteria andMeterStateIsNull() {
            addCriterion("meter_state is null");
            return (Criteria) this;
        }

        public Criteria andMeterStateIsNotNull() {
            addCriterion("meter_state is not null");
            return (Criteria) this;
        }

        public Criteria andMeterStateEqualTo(String value) {
            addCriterion("meter_state =", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotEqualTo(String value) {
            addCriterion("meter_state <>", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateGreaterThan(String value) {
            addCriterion("meter_state >", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateGreaterThanOrEqualTo(String value) {
            addCriterion("meter_state >=", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateLessThan(String value) {
            addCriterion("meter_state <", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateLessThanOrEqualTo(String value) {
            addCriterion("meter_state <=", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateLike(String value) {
            addCriterion("meter_state like", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotLike(String value) {
            addCriterion("meter_state not like", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateIn(List<String> values) {
            addCriterion("meter_state in", values, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotIn(List<String> values) {
            addCriterion("meter_state not in", values, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateBetween(String value1, String value2) {
            addCriterion("meter_state between", value1, value2, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotBetween(String value1, String value2) {
            addCriterion("meter_state not between", value1, value2, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterTimeIsNull() {
            addCriterion("meter_time is null");
            return (Criteria) this;
        }

        public Criteria andMeterTimeIsNotNull() {
            addCriterion("meter_time is not null");
            return (Criteria) this;
        }

        public Criteria andMeterTimeEqualTo(Long value) {
            addCriterion("meter_time =", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeNotEqualTo(Long value) {
            addCriterion("meter_time <>", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeGreaterThan(Long value) {
            addCriterion("meter_time >", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("meter_time >=", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeLessThan(Long value) {
            addCriterion("meter_time <", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeLessThanOrEqualTo(Long value) {
            addCriterion("meter_time <=", value, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeIn(List<Long> values) {
            addCriterion("meter_time in", values, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeNotIn(List<Long> values) {
            addCriterion("meter_time not in", values, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeBetween(Long value1, Long value2) {
            addCriterion("meter_time between", value1, value2, "meterTime");
            return (Criteria) this;
        }

        public Criteria andMeterTimeNotBetween(Long value1, Long value2) {
            addCriterion("meter_time not between", value1, value2, "meterTime");
            return (Criteria) this;
        }

        public Criteria andPowerFactorIsNull() {
            addCriterion("power_factor is null");
            return (Criteria) this;
        }

        public Criteria andPowerFactorIsNotNull() {
            addCriterion("power_factor is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFactorEqualTo(BigDecimal value) {
            addCriterion("power_factor =", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorNotEqualTo(BigDecimal value) {
            addCriterion("power_factor <>", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorGreaterThan(BigDecimal value) {
            addCriterion("power_factor >", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_factor >=", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorLessThan(BigDecimal value) {
            addCriterion("power_factor <", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_factor <=", value, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorIn(List<BigDecimal> values) {
            addCriterion("power_factor in", values, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorNotIn(List<BigDecimal> values) {
            addCriterion("power_factor not in", values, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_factor between", value1, value2, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andPowerFactorNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_factor not between", value1, value2, "powerFactor");
            return (Criteria) this;
        }

        public Criteria andRowIdIsNull() {
            addCriterion("row_id is null");
            return (Criteria) this;
        }

        public Criteria andRowIdIsNotNull() {
            addCriterion("row_id is not null");
            return (Criteria) this;
        }

        public Criteria andRowIdEqualTo(Integer value) {
            addCriterion("row_id =", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotEqualTo(Integer value) {
            addCriterion("row_id <>", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdGreaterThan(Integer value) {
            addCriterion("row_id >", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("row_id >=", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdLessThan(Integer value) {
            addCriterion("row_id <", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdLessThanOrEqualTo(Integer value) {
            addCriterion("row_id <=", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdIn(List<Integer> values) {
            addCriterion("row_id in", values, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotIn(List<Integer> values) {
            addCriterion("row_id not in", values, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdBetween(Integer value1, Integer value2) {
            addCriterion("row_id between", value1, value2, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotBetween(Integer value1, Integer value2) {
            addCriterion("row_id not between", value1, value2, "rowId");
            return (Criteria) this;
        }

        public Criteria andVoltIsNull() {
            addCriterion("volt is null");
            return (Criteria) this;
        }

        public Criteria andVoltIsNotNull() {
            addCriterion("volt is not null");
            return (Criteria) this;
        }

        public Criteria andVoltEqualTo(BigDecimal value) {
            addCriterion("volt =", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltNotEqualTo(BigDecimal value) {
            addCriterion("volt <>", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltGreaterThan(BigDecimal value) {
            addCriterion("volt >", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("volt >=", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltLessThan(BigDecimal value) {
            addCriterion("volt <", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("volt <=", value, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltIn(List<BigDecimal> values) {
            addCriterion("volt in", values, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltNotIn(List<BigDecimal> values) {
            addCriterion("volt not in", values, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volt between", value1, value2, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("volt not between", value1, value2, "volt");
            return (Criteria) this;
        }

        public Criteria andVoltChangeIsNull() {
            addCriterion("volt_change is null");
            return (Criteria) this;
        }

        public Criteria andVoltChangeIsNotNull() {
            addCriterion("volt_change is not null");
            return (Criteria) this;
        }

        public Criteria andVoltChangeEqualTo(Integer value) {
            addCriterion("volt_change =", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeNotEqualTo(Integer value) {
            addCriterion("volt_change <>", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeGreaterThan(Integer value) {
            addCriterion("volt_change >", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("volt_change >=", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeLessThan(Integer value) {
            addCriterion("volt_change <", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeLessThanOrEqualTo(Integer value) {
            addCriterion("volt_change <=", value, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeIn(List<Integer> values) {
            addCriterion("volt_change in", values, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeNotIn(List<Integer> values) {
            addCriterion("volt_change not in", values, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeBetween(Integer value1, Integer value2) {
            addCriterion("volt_change between", value1, value2, "voltChange");
            return (Criteria) this;
        }

        public Criteria andVoltChangeNotBetween(Integer value1, Integer value2) {
            addCriterion("volt_change not between", value1, value2, "voltChange");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNull() {
            addCriterion("w_addr is null");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNotNull() {
            addCriterion("w_addr is not null");
            return (Criteria) this;
        }

        public Criteria andWAddrEqualTo(Integer value) {
            addCriterion("w_addr =", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotEqualTo(Integer value) {
            addCriterion("w_addr <>", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThan(Integer value) {
            addCriterion("w_addr >", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThanOrEqualTo(Integer value) {
            addCriterion("w_addr >=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThan(Integer value) {
            addCriterion("w_addr <", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThanOrEqualTo(Integer value) {
            addCriterion("w_addr <=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrIn(List<Integer> values) {
            addCriterion("w_addr in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotIn(List<Integer> values) {
            addCriterion("w_addr not in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrBetween(Integer value1, Integer value2) {
            addCriterion("w_addr between", value1, value2, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotBetween(Integer value1, Integer value2) {
            addCriterion("w_addr not between", value1, value2, "wAddr");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}