export interface Account {
    accountId:               string;
    balance:                 number;
    currentPage:             number;
    totalPages:              number;
    pageSize:                number;
    accountOperationListDTO: AccountOperationList[];
}

export interface AccountOperationList {
    id:            number;
    operationDate: Date;
    amount:        number;
    operationType: string;
    description:   string;
}
