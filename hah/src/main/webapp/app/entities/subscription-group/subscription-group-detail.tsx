import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subscription-group.reducer';
import { ISubscriptionGroup } from 'app/shared/model/subscription-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubscriptionGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubscriptionGroupDetail = (props: ISubscriptionGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { subscriptionGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.subscriptionGroup.detail.title">SubscriptionGroup</Translate> [<b>{subscriptionGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="hahApp.subscriptionGroup.name">Name</Translate>
            </span>
          </dt>
          <dd>{subscriptionGroupEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/subscription-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subscription-group/${subscriptionGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ subscriptionGroup }: IRootState) => ({
  subscriptionGroupEntity: subscriptionGroup.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubscriptionGroupDetail);
