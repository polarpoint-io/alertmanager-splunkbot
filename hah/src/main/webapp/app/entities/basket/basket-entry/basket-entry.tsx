import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './basket-entry.reducer';
import { IBasketEntry } from 'app/shared/model/basket/basket-entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IBasketEntryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BasketEntry = (props: IBasketEntryProps) => {
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

  const { basketEntryList, match, totalItems } = props;
  return (
    <div>
      <h2 id="basket-entry-heading">
        <Translate contentKey="hahApp.basketBasketEntry.home.title">Basket Entries</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.basketBasketEntry.home.createLabel">Create new Basket Entry</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {basketEntryList && basketEntryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('basketId')}>
                  <Translate contentKey="hahApp.basketBasketEntry.basketId">Basket Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('canEdit')}>
                  <Translate contentKey="hahApp.basketBasketEntry.canEdit">Can Edit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prodictId')}>
                  <Translate contentKey="hahApp.basketBasketEntry.prodictId">Prodict Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  <Translate contentKey="hahApp.basketBasketEntry.quantity">Quantity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('refundable')}>
                  <Translate contentKey="hahApp.basketBasketEntry.refundable">Refundable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('removeable')}>
                  <Translate contentKey="hahApp.basketBasketEntry.removeable">Removeable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalPrice')}>
                  <Translate contentKey="hahApp.basketBasketEntry.totalPrice">Total Price</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('transactionId')}>
                  <Translate contentKey="hahApp.basketBasketEntry.transactionId">Transaction Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('unitPrice')}>
                  <Translate contentKey="hahApp.basketBasketEntry.unitPrice">Unit Price</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vatCode')}>
                  <Translate contentKey="hahApp.basketBasketEntry.vatCode">Vat Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vatElement')}>
                  <Translate contentKey="hahApp.basketBasketEntry.vatElement">Vat Element</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hahApp.basketBasketEntry.basket">Basket</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {basketEntryList.map((basketEntry, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${basketEntry.id}`} color="link" size="sm">
                      {basketEntry.id}
                    </Button>
                  </td>
                  <td>{basketEntry.basketId}</td>
                  <td>{basketEntry.canEdit ? 'true' : 'false'}</td>
                  <td>{basketEntry.prodictId}</td>
                  <td>{basketEntry.quantity}</td>
                  <td>{basketEntry.refundable ? 'true' : 'false'}</td>
                  <td>{basketEntry.removeable ? 'true' : 'false'}</td>
                  <td>{basketEntry.totalPrice}</td>
                  <td>{basketEntry.transactionId}</td>
                  <td>{basketEntry.unitPrice}</td>
                  <td>{basketEntry.vatCode}</td>
                  <td>{basketEntry.vatElement}</td>
                  <td>{basketEntry.basket ? <Link to={`basket/${basketEntry.basket.id}`}>{basketEntry.basket.basketId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${basketEntry.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${basketEntry.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${basketEntry.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
            <Translate contentKey="hahApp.basketBasketEntry.home.notFound">No Basket Entries found</Translate>
          </div>
        )}
      </div>
      <div className={basketEntryList && basketEntryList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ basketEntry }: IRootState) => ({
  basketEntryList: basketEntry.entities,
  totalItems: basketEntry.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BasketEntry);
