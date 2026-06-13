package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);
    }

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        List<AddressBook> list=addressBookMapper.list(addressBook);
        return list;
    }

    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook=addressBookMapper.getById(id);
        return addressBook;
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override
    public void delete(Long id) {
        addressBookMapper.delete(id);
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        //先要把当前用户的其他地址都设置为非默认，再把此条设置为默认
        AddressBook addressBook1=new AddressBook();
        addressBook1.setUserId(BaseContext.getCurrentId());
        addressBook1.setIsDefault(1);
        List<AddressBook> addressBookList=addressBookMapper.list(addressBook1);
        if(addressBookList.size()==1){
            AddressBook item=addressBookList.get(0);
            item.setIsDefault(0);
            addressBookMapper.update(item);
        }
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
