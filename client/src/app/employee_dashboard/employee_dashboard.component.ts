import { Component, OnInit } from '@angular/core';

import { RequestService, AuthenticationService, EmployeeService } from '../_services';
import { CitizenRequest, User, Employee } from '../_models';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';

@Component({
    templateUrl: 'employee_dashboard.component.html'
})

export class EmployeeDashboardComponent implements OnInit {

    currentUser: User;
    selectedRequest: CitizenRequest;
    serviceRequests$: Observable<CitizenRequest[]>;
    employee: Employee;

    constructor(private requestService: RequestService, private employeeService: EmployeeService,
                    private authService: AuthenticationService) {}

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.serviceRequests$ = this. employeeService.getEmployeeById(this.currentUser.id)
                                    .flatMap(employee => {
                                        this.employee = employee;
                                        return this.requestService.getDepartmentRequests(employee.id);
                                    });
    }

    assign (request: CitizenRequest) {
        this.requestService.assignThisRequest(this.currentUser.id, request)
            .subscribe( success => {
                if (success) {
                    // refresh requests
                    this.serviceRequests$ = this.requestService.getDepartmentRequests(this.employee.id);
                }
            });
    }
}
