package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.Department;
import com.example.UniversityManagementSystem.model.Teacher;
import com.example.UniversityManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public boolean saveDepartment(Department department){

        if(departmentRepository.existsById(department.getCode())){
            return true;
        }
        else{
            department= departmentRepository.save(department);
            return false;
        }

    }

    public List<Department> findAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDeptbyId(Department department) {
        return departmentRepository.findById(department.getCode()).get();
    }

    public List<Teacher> findAllTeacherByDepartmentCode(String departmentCode){
        Department department= departmentRepository.findById(departmentCode).get();
        return department.getTeachers();
    }

    public long numberOfDepartmentExist(){
        return departmentRepository.count();
    }

    public Page<Department> findAllDepartmentsWithPagination(int currentPage) {
        int pageSize=10;
        Pageable pageable= PageRequest.of(currentPage-1, pageSize, Sort.by("code").ascending());
        Page<Department> departmentPage= departmentRepository.findAll(pageable);
        return departmentPage;

    }

    public boolean isDepartmentExist(Department department) {
        return departmentRepository.existsById(department.getCode());
    }

    public boolean saveUpdatedDepartment(Department departmentCarry) {
        Department department=departmentRepository.findById(departmentCarry.getCode()).orElseThrow();
        if(departmentCarry.getName()!=null){
            department.setName(departmentCarry.getName());
        }
        department=departmentRepository.save(department);
        if(department.getName().equalsIgnoreCase(departmentCarry.getName())){
            return true;
        }
        else{
            return false;
        }


    }

    public boolean deleteDepartment(Department department) {
        departmentRepository.delete(departmentRepository.findById(department.getCode()).orElseThrow());
        if(departmentRepository.existsById(department.getCode())){
            return false;
        }
        return true;

    }
}

