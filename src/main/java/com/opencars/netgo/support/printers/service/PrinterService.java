package com.opencars.netgo.support.printers.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.printers.entity.Printer;
import com.opencars.netgo.support.printers.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrinterService {

    @Autowired
    PrinterRepository printerRepository;

    public Optional<Printer> getOne(int id){
        return printerRepository.findById(id);
    }

    public Page<Printer> getByState(String state, Pageable pageable){
        return printerRepository.findByState(state, pageable);
    }

    public Page<Printer> getAll(Pageable pageable){
        return printerRepository.findAll(pageable);
    }

    public Page<Printer> getList(Pageable pageable){
        return printerRepository.findAll(pageable);
    }

    public int getCountTotalPrinters(){
        return printerRepository.countTotalPrinters();
    }

    public int getCountTotalScanners(){
        return printerRepository.countTotalScanners();
    }


    public int getCountTotalPrintersByBranch(int branch){
        return printerRepository.countTotalPrintersByBranch(branch);
    }

    public int getCountTotalScannersByBranch(int branch){
        return printerRepository.countTotalScannersByBranch(branch);
    }


    public int getCountAssignedsPrinters(){
        return printerRepository.countAssignedsPrinters();
    }

    public int getCountAssignedsScanners(){
        return printerRepository.countAssignedsScanner();
    }

    public int getCountAssignedsPrintersByBranch(int branch){
        return printerRepository.countAssignedsPrintersByBranch(branch);
    }

    public int getCountAssignedsScannersByBranch(int branch){
        return printerRepository.countAssignedsScannersByBranch(branch);
    }


    public int getCountStockPrintersByBranch(int branch){
        return printerRepository.countStockPrintersByBranch(branch);
    }

    public int getCountStockScannersByBranch(int branch){
        return printerRepository.countStockScannersByBranch(branch);
    }

    public int getCountStockPrinters(){
        return printerRepository.countStockPrinters();
    }

    public int getCountStockScanners(){
        return printerRepository.countStockScanners();
    }

    public List<Printer> getPrintersByBranch(Branch branch){
        return printerRepository.findByBranch(branch);
    }

    public void deleteById(int id){
        printerRepository.deleteById(id);
    }

    public void save(Printer printer){
        printerRepository.save(printer);
    }
}
