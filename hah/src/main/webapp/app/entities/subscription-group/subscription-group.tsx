import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subscription-group.reducer';
import { ISubscriptionGroup } from 'app/shared/model/subscription-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubscriptionGroupProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SubscriptionGroup = (props: ISubscriptionGroupProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { subscriptionGroupList, match } = props;
  return (
    <div>
      <h2 id="subscription-group-heading">
        <Translate contentKey="hahApp.subscriptionGroup.home.title">Subscription Groups</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.subscriptionGroup.home.createLabel">Create new Subscription Group</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {subscriptionGroupList && subscriptionGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.subscriptionGroup.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subscriptionGroupList.map((subscriptionGroup, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${subscriptionGroup.id}`} color="link" size="sm">
                      {subscriptionGroup.id}
                    </Button>
                  </td>
                  <td>{subscriptionGroup.name}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${subscriptionGroup.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subscriptionGroup.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subscriptionGroup.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="hahApp.subscriptionGroup.home.notFound">No Subscription Groups found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ subscriptionGroup }: IRootState) => ({
  subscriptionGroupList: subscriptionGroup.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubscriptionGroup);
