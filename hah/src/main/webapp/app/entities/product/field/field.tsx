import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './field.reducer';
import { IField } from 'app/shared/model/product/field.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFieldProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Field = (props: IFieldProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  useEffect(() => {
    getAllEntities();
  }, []);

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });

  const { fieldList, match, totalItems } = props;
  return (
    <div>
      <h2 id="field-heading">
        <Translate contentKey="hahApp.productField.home.title">Fields</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.productField.home.createLabel">Create new Field</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {fieldList && fieldList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultz')}>
                  <Translate contentKey="hahApp.productField.defaultz">Defaultz</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('editable')}>
                  <Translate contentKey="hahApp.productField.editable">Editable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('label')}>
                  <Translate contentKey="hahApp.productField.label">Label</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mandatory')}>
                  <Translate contentKey="hahApp.productField.mandatory">Mandatory</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxs')}>
                  <Translate contentKey="hahApp.productField.maxs">Maxs</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mins')}>
                  <Translate contentKey="hahApp.productField.mins">Mins</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('multiple')}>
                  <Translate contentKey="hahApp.productField.multiple">Multiple</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="hahApp.productField.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('patternz')}>
                  <Translate contentKey="hahApp.productField.patternz">Patternz</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="hahApp.productField.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fieldList.map((field, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${field.id}`} color="link" size="sm">
                      {field.id}
                    </Button>
                  </td>
                  <td>{field.defaultz}</td>
                  <td>{field.editable ? 'true' : 'false'}</td>
                  <td>{field.label}</td>
                  <td>{field.mandatory ? 'true' : 'false'}</td>
                  <td>{field.maxs}</td>
                  <td>{field.mins}</td>
                  <td>{field.multiple}</td>
                  <td>{field.name}</td>
                  <td>{field.patternz}</td>
                  <td>{field.type}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${field.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${field.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${field.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
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
            <Translate contentKey="hahApp.productField.home.notFound">No Fields found</Translate>
          </div>
        )}
      </div>
      <div className={fieldList && fieldList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({ field }: IRootState) => ({
  fieldList: field.entities,
  totalItems: field.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Field);
