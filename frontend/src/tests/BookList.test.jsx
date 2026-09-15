import { render, screen } from '@testing-library/react';
import BookList from '../components/BookList';
import * as api from '../services/api';

jest.mock('../services/api');
jest.mock('../context/CartContext', () => ({
    CartContext: { Consumer: ({children}) => children({refreshCart: jest.fn(), userId:'test'}) },
    useContext: () => ({refreshCart: jest.fn(), userId:'test'})
}));

test('should display books', async () => {
    api.getBooks.mockResolvedValue({data: [{id:1, title:'Clean Code', author:'Robert', price:40, stock:10, isbn:'123'}]});
    render(<BookList />);
    expect(await screen.findByText('Clean Code')).toBeInTheDocument();
});