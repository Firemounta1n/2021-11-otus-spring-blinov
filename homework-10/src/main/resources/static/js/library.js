function loadBooks() {
    $.get('/api/books').done(function (books) {
        $('tbody').empty();

        books.forEach(function (book) {
            $('tbody').append(`
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.author.fio}</td>
                        <td>${book.genre.name}</td>
                        <td>${book.comments.length}</td>
                        <td>
                            <a href="#" onclick="editBook(${book.id})">Edit</a>
                            <a href="#" onclick="deleteBook(${book.id})">Delete</a>
                        </td>     
                    </tr>
                `)
        });
    })
}

function editBook(bookId) {
    showEditor(bookId);

    $.get('/api/book/' + bookId).done(function (book) {
        $('#bookId').val(book.id);
        $('#bookTitle').val(book.title);
        $('#bookAuthorId').val(book.author.id);
        $('#bookGenreId').val(book.genre.id);
    })
}

function showList() {
    $('#bookEditor').hide();
    clearEditBlock();

    loadBooks();
    $('#bookList').show();
}

function clearEditBlock() {
    $('#bookId').val('');
    $('#bookTitle').val('');
    $('#bookAuthorId').val('');
    $('#bookGenreId').val('');
}

function showEditor() {
    loadAuthors();
    loadGenres();
    $('#bookList').hide();
    $('#bookEditor').show();
}

function saveBook() {
    let id = $('#bookId').val();
    let method = (id === '') ? 'POST' : 'PUT';

    $.ajax({
        url: '/api/book',
        type: method,
        data: JSON.stringify({
            id: id,
            title: $('#bookTitle').val(),
            author: {
                id: $('#bookAuthorId').val()
            },
            genre: {
                id: $('#bookGenreId').val()
            },
        }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        statusCode:{
            201 : function() {
                showList();
            },
            202 : function () {
                showList();
            }
        }
    });
}

function deleteBook(id) {
    if (!confirm('Delete this book?')) {
        return;
    }

    $.ajax({
        url: '/api/book/' + id,
        type: 'DELETE',
        statusCode:{
            202 : function(){
                showList();
            }
        }
    });
}

function loadAuthors() {
    $.get('/api/authors').done(function (authors) {
        $('#bookAuthorId').empty();

        authors.forEach(function (author) {
            $('#bookAuthorId').append(`
                        <option value="${author.id}">${author.fio}</option>
                `)
        });
    })
}

function loadGenres() {
    $.get('/api/genres').done(function (genres) {
        $('#bookGenreId').empty();

        genres.forEach(function (genre) {
            $('#bookGenreId').append(`
                        <option value="${genre.id}">${genre.name}</option>
                `)
        });
    })
}

